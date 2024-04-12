package com.deloitte.springboot.entities;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.SQLRestriction;

import com.deloitte.springboot.generators.CustomGenerator;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "PRODUCTS")
@SQLRestriction("status=1")
public class Product {
    @Id
    @GenericGenerator(name = "custom_id", type = CustomGenerator.class)
    @GeneratedValue(generator = "custom_id")
    @Column(name = "PRODUCT_ID")
    private BigDecimal id;
    @Column(name = "NAME")
    private String name;
    @Column(name = "PRICE")
    private BigDecimal price;
    @Column(name = "IMAGE")
    private String image;
    @Column(name = "DESCRIPTION")
    private String description;
    @Column(name = "TOTAL_PRODUCTS_INVENTORY")
    private BigDecimal totalProductsInventory;
    @Column(name = "STATUS")
    private boolean status = true;
    @OneToMany(mappedBy = "product", cascade = CascadeType.MERGE)
    private Set<OrderHistory> orderHistories;
    @OneToMany(mappedBy = "product", cascade = CascadeType.MERGE)
    private Set<Wishlist> wishLists;

    public Product() { }

    public Product(String name, BigDecimal price, String image, String description, BigDecimal totalProductsInventory) {
        this.name = name;
        this.price = price;
        this.image = image;
        this.description = description;
        this.totalProductsInventory = totalProductsInventory;
    }

    public BigDecimal getId() {
        return this.id;
    }

    public void setId(BigDecimal id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPrice() {
        return this.price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getImage() {
        return this.image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getTotalProductsInventory() {
        return this.totalProductsInventory;
    }

    public void setTotalProductsInventory(BigDecimal totalProductsInventory) {
        this.totalProductsInventory = totalProductsInventory;
    }

    public boolean isStatus() {
        return this.status;
    }

    public boolean getStatus() {
        return this.status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    @JsonIgnore
    public Set<OrderHistory> getOrderHistories() {
        return this.orderHistories;
    }

    public void setOrderHistories(Set<OrderHistory> orderHistories) {
        this.orderHistories = orderHistories;
    }

    public Product id(BigDecimal id) {
        setId(id);
        return this;
    }

    public Product name(String name) {
        setName(name);
        return this;
    }

    public Product price(BigDecimal price) {
        setPrice(price);
        return this;
    }

    public Product image(String image) {
        setImage(image);
        return this;
    }

    public Product description(String description) {
        setDescription(description);
        return this;
    }

    public Product totalProductsInventory(BigDecimal totalProductsInventory) {
        setTotalProductsInventory(totalProductsInventory);
        return this;
    }

    public Product status(boolean status) {
        setStatus(status);
        return this;
    }

    public Product orderHistories(Set<OrderHistory> orderHistories) {
        setOrderHistories(orderHistories);
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof Product)) {
            return false;
        }
        Product product = (Product) o;
        return Objects.equals(name, product.name) && Objects.equals(price, product.price) && Objects.equals(image, product.image) && Objects.equals(description, product.description) && Objects.equals(totalProductsInventory, product.totalProductsInventory) && status == product.status;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, price, image, description, totalProductsInventory, status);
    }

    @Override
    public String toString() {
        return "{" +
            " id='" + getId() + "'" +
            ", name='" + getName() + "'" +
            ", price='" + getPrice() + "'" +
            ", image='" + getImage() + "'" +
            ", description='" + getDescription() + "'" +
            ", totalProductsInventory='" + getTotalProductsInventory() + "'" +
            ", status='" + isStatus() + "'" +
            "}";
    }

    public void placeOrder(User user) {
        OrderHistory orderHistory = new OrderHistory(user, this);
        if (orderHistories != null) {
            orderHistories = new HashSet<>();
        }
        orderHistories.add(orderHistory);
    }
}
