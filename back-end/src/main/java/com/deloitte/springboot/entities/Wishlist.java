package com.deloitte.springboot.entities;

import java.math.BigDecimal;

import org.hibernate.annotations.GenericGenerator;

import com.deloitte.springboot.generators.CustomGenerator;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import java.util.Objects;

@Entity
@Table(name = "WISHLIST")
public class Wishlist {
    @Id
    @GenericGenerator(name = "custom_id", type = CustomGenerator.class)
    @GeneratedValue(generator = "custom_id")
    @Column(name = "WISH_ID")
    private BigDecimal id;
    @ManyToOne
    @JoinColumn(name = "USER_ID")
    private User user;
    @ManyToOne
    @JoinColumn(name = "PRODUCT_ID")
    private Product product;

    public Wishlist() { }

    public Wishlist(User user, Product product) {
        this.user = user;
        this.product = product;
    }

    public BigDecimal getId() {
        return this.id;
    }

    public void setId(BigDecimal id) {
        this.id = id;
    }

    @JsonIgnore
    public User getUser() {
        return this.user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @JsonIgnore
    public Product getProduct() {
        return this.product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Wishlist id(BigDecimal id) {
        setId(id);
        return this;
    }

    public Wishlist user(User user) {
        setUser(user);
        return this;
    }

    public Wishlist product(Product product) {
        setProduct(product);
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof Wishlist)) {
            return false;
        }
        Wishlist wishList = (Wishlist) o;
        return Objects.equals(user, wishList.user) && Objects.equals(product, wishList.product);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, user, product);
    }

    @Override
    public String toString() {
        return "{" +
            " id='" + getId() + "'" +
            ", user='" + getUser() + "'" +
            ", product='" + getProduct() + "'" +
            "}";
    }    
}
