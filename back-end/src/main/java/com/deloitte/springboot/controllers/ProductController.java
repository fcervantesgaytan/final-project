package com.deloitte.springboot.controllers;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.deloitte.springboot.entities.OrderHistory;
import com.deloitte.springboot.entities.Product;
import com.deloitte.springboot.entities.User;
import com.deloitte.springboot.entities.Wishlist;
import com.deloitte.springboot.exceptions.ProductConflictException;
import com.deloitte.springboot.repositories.OrderHistoryRepository;
import com.deloitte.springboot.repositories.ProductRepository;
import com.deloitte.springboot.repositories.UserRepository;
import com.deloitte.springboot.repositories.WishlistRepository;

@RestController
@RequestMapping("/api")
public class ProductController {
    @Autowired
    ProductRepository productRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    OrderHistoryRepository orderHistoryRepository; 
    @Autowired
    WishlistRepository wishlistRepository;

    /**
     * Returns all the products.
     * 
     * @return A List with all the products.
     */
    @GetMapping("/products")
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @GetMapping("/products/{id}")
    public Product getProduct(@PathVariable("id") BigDecimal id) {
        return productRepository.findById(id).get();
    }

    /**
     * Pageable request
     * 
     * @param pageNumber
     * @param pageSize
     * @return Paginated results
     */
    @GetMapping("/products/paginate")
    public List<Product> getPaginatedProducts(
        @RequestParam("pageNumber") int pageNumber,
        @RequestParam("pageSize") int pageSize
    ) {
        return productRepository.findAll(PageRequest.of(pageNumber, pageSize)).getContent();
    }
    

    /**
     * It will fetch the product with the specified email.
     * 
     * @param name The name of the product to search for.
     * @return The Product that matches the name.
     */
    @GetMapping("/products/searchname")
    public Product getProductByName(@RequestParam(name = "name") String name) {
        return productRepository.findByName(name).get();
    }

    /**
     * It will fetch all the products within the specified range.
     * 
     * @param minPrice The minimum price of the product to search for.
     * @param maxPrice The maximum price of the product to search for.
     * @return A List containing all products that match.
     */
    @GetMapping("/products/searchprice")
    public List<Product> getProductsByPriceRange(
        @RequestParam(name = "minPrice") BigDecimal minPrice,
        @RequestParam(name = "maxPrice") BigDecimal maxPrice
    ) {
        return productRepository.findByPriceBetween(minPrice, maxPrice);
    }

    /**
     * Checks if the product already exists searching by name.
     * If it exists it increases the total number of products by 1,
     * if it does not exist then it is created.
     * 
     * @param product The product to create.
     * @return The created Product
     */
    @PostMapping("/products")
    public Product saveProduct(@RequestBody Product product) {
        if (productRepository.existsByName(product.getName())) {
            product = productRepository.findByName(product.getName()).get();
            product.setTotalProductsInventory(product.getTotalProductsInventory().add(new BigDecimal(1)));
        }

        return productRepository.save(product);
    }
    
    /**
     * Updates product based on id.
     * 
     * @param id The id of the product to update.
     * @param product The product containing the updated info.
     * @return The updated Product.
     */
    @PutMapping("/products/{id}")
    public Product updateProduct(@PathVariable BigDecimal id, @RequestBody Product product) {
        Product productToUpdate = productRepository.findById(id).get();
        BigDecimal newPrice = product.getPrice();
        String newImage = product.getImage();
        String newDescription = product.getDescription();
        BigDecimal newTotalProductsInventory = product.getTotalProductsInventory();

        if (newPrice != null) {
            productToUpdate.setPrice(newPrice);
        }
        if (newImage != null) {
            productToUpdate.setImage(newImage);
        }
        if (newDescription != null) {
            productToUpdate.setDescription(newDescription);
        }
        if (newTotalProductsInventory != null) {
            productToUpdate.setTotalProductsInventory(newTotalProductsInventory);
        }

        return productRepository.save(productToUpdate);
    }

    /**
     * If the product exists then it will soft delete it.
     * 
     * @param id The id of the product to delete.
     */
    @DeleteMapping("/products/{id}")
    public void deleteProduct(@PathVariable BigDecimal id) {
        Product product = productRepository.findById(id).get();
        product.setStatus(false);
        productRepository.save(product);
    }

    /**
     * Places an order with a Product for an User.
     * If the stock for the product is non existent then it will
     * be informed.
     * 
     * @param productId
     * @param userId
     * @return The product that was placed in the Order.
     */
    @PostMapping("/products/{productId}/buy/{userId}")
    public Product buyProduct(@PathVariable BigDecimal productId, @PathVariable BigDecimal userId) {
        User user = userRepository.findById(userId).get();
        Product product = productRepository.findById(productId).get();
        if (product.getTotalProductsInventory().equals(new BigDecimal(0))) {
            throw new ProductConflictException("There is no more inventory for the product: " + product.getName());
        }

        product.setTotalProductsInventory(product.getTotalProductsInventory().subtract(new BigDecimal(1)));
        product.placeOrder(user);
        product = productRepository.save(product);
        return product;
    }

    @PostMapping("/products/buy/{userId}")
    public List<Product> buyProducts(@PathVariable BigDecimal userId, @RequestBody List<Product> products) {
        User user = userRepository.findById(userId).get();
        List<Product> updatedProducts = products.stream().map((product) -> {
            if (product.getTotalProductsInventory().equals(new BigDecimal(0))) {
                throw new ProductConflictException("There is no more inventory for the product: " + product.getName());
            }

            List<OrderHistory> orderHistories = orderHistoryRepository.findByUser(user);
            product.setOrderHistories(orderHistories.stream().collect(Collectors.toSet()));
            product.placeOrder(user);
            product.setTotalProductsInventory(product.getTotalProductsInventory().subtract(new BigDecimal(1)));

            if (wishlistRepository.existsByUserIdAndProductId(userId, product.getId())) {
                Wishlist wishlist = wishlistRepository.findByUserIdAndProductId(userId, product.getId()).get();
                wishlistRepository.delete(wishlist);
            }
            
            return product;
        })
        .toList();

        return productRepository.saveAll(updatedProducts);
    }
    
    @GetMapping("/products/count")
    public long countProducts() {
        return productRepository.count();
    }

}
