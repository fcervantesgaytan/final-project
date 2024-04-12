package com.deloitte.springboot.controllers;

import java.math.BigDecimal;
import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.deloitte.springboot.entities.Product;
import com.deloitte.springboot.entities.User;
import com.deloitte.springboot.entities.Wishlist;
import com.deloitte.springboot.exceptions.UserConflictException;
import com.deloitte.springboot.repositories.ProductRepository;
import com.deloitte.springboot.repositories.UserRepository;
import com.deloitte.springboot.repositories.WishlistRepository;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;


@RestController
@RequestMapping("/api")
public class UserController {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private WishlistRepository wishlistRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * Returns all the users.
     * 
     * @return A List with all the users.
     */
    @GetMapping("/users")
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    /**
     * Retrieves a product based on ID.
     * 
     * @param id The id for the User to retrieve.
     * @return
     */
    @GetMapping("/users/{id}")
    public User getUser(@PathVariable BigDecimal id) {
        return userRepository.findById(id).get();
    }
    
    
    /**
     * It will fetch all users with the specified first names and last names.
     * 
     * @param firstName The first name of the users to search. Required.
     * @param lastName The last name of the users to search. Required.
     * @return A List containing all users that match.
     */
    @GetMapping("/users/searchname")
    public List<User> getUsersByName(
        @RequestParam(name = "firstName") String firstName,
        @RequestParam(name = "lastName") String lastName
    ) {
        return userRepository.findByNameAndLastName(firstName, lastName);
    }

    /**
     * It will fetch the user with the specified email.
     * 
     * @param email The email of the user to search.
     * @return The user that matches the email.
     */
    @GetMapping("/users/searchemail")
    public User getUserByEmail(@RequestParam(name = "email") String email) {
        return userRepository.findByEmail(email).get();
    }
    

    /**
     * Checks if the user already exists searching by email.
     * If it exists then it will inform it.
     * 
     * @param user The user to create.
     * @return The created User.
     */
    @PostMapping("/users")
    public User saveUser(@RequestBody User user) {
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new UserConflictException("User Already Exists");
        }

        return userRepository.save(user);
    }
    
    /**
     * Updates a product based on ID.
     * 
     * @param id The id of the User to update.
     * @param user The user containing the updated info.
     * @return
     */
    @PutMapping("/users/{id}")
    public User updateUser(@PathVariable BigDecimal id, @RequestBody User user) {
        User userToUpdate = userRepository.findById(id).get();
        String newName = user.getName();
        String newLastName = user.getLastName();
        String newEmail = user.getEmail();
        String newAreaOfInterest = user.getAreaOfInterest();
        String bio = user.getBio();
        String password = user.getPassword();
        String profilePictureUrl = user.getProfilePictureUrl();
        
        if (newEmail != null) {
            if (!userRepository.existsByEmail(newEmail)) {
                userToUpdate.setEmail(newEmail);
            } else if (! newEmail.equals(userToUpdate.getEmail())) {
                throw new UserConflictException("Email Already Exists");
            }
        }

        if (newName != null) {
            userToUpdate.setName(newName);
        }
        if (newLastName != null) {
            userToUpdate.setLastName(newLastName);
        }
        if (newAreaOfInterest != null) {
            userToUpdate.setAreaOfInterest(user.getAreaOfInterest());
        }
        if (bio != null) {
            userToUpdate.setBio(bio);
        }
        if (password != null) {
            userToUpdate.setPassword(passwordEncoder.encode(password));
        }
        if (profilePictureUrl != null) {
            userToUpdate.setProfilePictureUrl(profilePictureUrl);
        }
            
        return userRepository.save(userToUpdate);        
    }

    /**
     * Checks if the user exists with the id.
     * If it exists then it will delete it,
     * it it does not exist then it will inform it.
     * 
     * @param id
     */
    @DeleteMapping("/users/{id}")
    public void deleteUser(@PathVariable BigDecimal id) {
        if (! userRepository.existsById(id)) {
            throw new NoSuchElementException();
        }
        
        userRepository.deleteById(id);
    }
    
    /**
     * Retrieves a WishList for the user with a given ID.
     * The wishlit will be represented as List of Products
     * 
     * @param id The ID for the user that will get the wishlist.
     * @return A List with the Products of the wishlist.
     */
    @GetMapping("/users/{id}/wishlist")
    public List<Product> getWishList(@PathVariable BigDecimal id) {
        User user = userRepository.findById(id).get();
        List<Product> wishListProducts = user.getWishListAsProductList();
            
        return wishListProducts;
    }

    /**
     * Adds a Product with a given ID to the User
     * with the given ID.
     * 
     * @param userId The ID for the user that will be added a Product to his wishlist.
     * @param productId The ID for the Product that will be added to User's wishlist.
     * @return A List with the Products of the wishlist.
     */
    @PostMapping("/users/{userId}/wishlist/{productId}")
    public List<Product> addProductToWishList(@PathVariable BigDecimal userId, @PathVariable BigDecimal productId) {
        User user = userRepository.findById(userId).get();
        Product product = productRepository.findById(productId).get();
        if (wishlistRepository.existsByUserIdAndProductId(userId, productId)) {
            return user.getWishListAsProductList();
        }

        user.addProductToWishList(product);
        userRepository.save(user);

        return user.getWishListAsProductList();        
    }

    /**
     * Deletes a product from an User's wishlist.
     * 
     * @param userId The ID for the User that will get a Product eliminated from his wishlist.
     * @param productId The ID for the Product that will be eliminated from the User's wishlist.
     */
    @DeleteMapping("/users/{userId}/wishlist/{productId}")
    public void deleteProductFromWishList(@PathVariable BigDecimal userId, @PathVariable BigDecimal productId) {
        User user = userRepository.findById(userId).get();
        Product product = productRepository.findById(productId).get();
        Wishlist wishlist = wishlistRepository.findByUserAndProduct(user, product).get();
            
        wishlistRepository.delete(wishlist);
    }

    /**
     * Deletes all the products from an User's wishlist.
     * 
     * @param id The ID for the User that will get his whishlist eliminated.
     */
    @DeleteMapping("/users/{id}/wishlist")
    public void clearWishList(@PathVariable BigDecimal id) {
        User user = userRepository.findById(id).get();
        List<Wishlist> wishlists = wishlistRepository.findByUser(user);
        
        if (wishlists != null) {
            wishlistRepository.deleteAll(wishlists);
        }
    }
    
}
