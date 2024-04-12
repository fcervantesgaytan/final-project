package com.deloitte.springboot.entities;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import java.math.BigDecimal;
import java.util.Set;
import java.util.stream.Collectors;

import org.hibernate.annotations.GenericGenerator;

import com.deloitte.springboot.generators.CustomGenerator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "USERS")
public class User {
    @Id
    @GenericGenerator(name = "custom_id", type = CustomGenerator.class)
    @GeneratedValue(generator = "custom_id")
    @Column(name = "USER_ID")
    private BigDecimal id;
    @Column(name = "EMAIL")
    private String email;
    @Column(name = "NAME")
    private String name;
    @Column(name = "LAST_NAME")
    private String lastName;
    @Column(name = "BIO")
    private String bio;
    @Column(name = "AREA_OF_INTEREST")
    private String areaOfInterest;
    @Column(name = "PASSWORD")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;
    @Column(name = "PROFILE_PICTURE_URL")
    private String profilePictureUrl;
    @OneToMany(mappedBy = "user", cascade = CascadeType.MERGE)
    private Set<OrderHistory> orderHistories;    
    @OneToMany(mappedBy = "user", cascade = CascadeType.MERGE)
    private Set<Wishlist> wishLists;
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "users_roles",
        joinColumns = @JoinColumn(name = "USER_ID", referencedColumnName = "USER_ID"),
            inverseJoinColumns = @JoinColumn(name = "ROLE_ID", referencedColumnName = "ROLE_ID")
    )
    private Set<Role> roles;

    public User() { }

    public User(String email, String name, String lastName, String bio, String areaOfInterest, String password) {
        this.email = email;
        this.name = name;
        this.lastName = lastName;
        this.bio = bio;
        this.areaOfInterest = areaOfInterest;
        this.password = password;
    }

    public BigDecimal getId() {
        return this.id;
    }

    public void setId(BigDecimal id) {
        this.id = id;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getProfilePictureUrl() {
        return this.profilePictureUrl;
    }

    public void setProfilePictureUrl(String profilePictureUrl) {
        this.profilePictureUrl = profilePictureUrl;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return this.lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getBio() {
        return this.bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getAreaOfInterest() {
        return this.areaOfInterest;
    }

    public void setAreaOfInterest(String areaOfInterest) {
        this.areaOfInterest = areaOfInterest;
    }

    @JsonIgnore
    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @JsonIgnore
    public Set<OrderHistory> getOrderHistories() {
        return this.orderHistories;
    }

    public void setWishLists(Set<Wishlist> wishLists) {
        this.wishLists = wishLists;
    }

    public Set<Wishlist> addProductToWishList(Product product) {
        Wishlist wishListItem = new Wishlist(this, product);
        if (wishLists != null && !wishLists.isEmpty()) {
            wishLists.add(wishListItem);
        } else {
            wishLists = new HashSet<Wishlist>(Set.of(wishListItem));
        }

        return wishLists;
    } 

    public Set<Wishlist> deleteProductFromWishList(Product product) {
        wishLists = wishLists.stream()
            .filter(wl -> ! wl.getProduct().getId().equals(product.getId()))
            .collect(Collectors.toSet());

        return wishLists;
    } 

    @JsonIgnore
    public Set<Wishlist> getWishLists() {
        return this.wishLists;
    }

    @JsonIgnore
    public List<Product> getWishListAsProductList() {
        return this.getWishLists()
            .stream()
            .map(Wishlist::getProduct)
            .collect(Collectors.toList());
    }

    public void setOrderHistories(Set<OrderHistory> orderHistories) {
        this.orderHistories = orderHistories;
    }

    public Set<Role> getRoles() {
        return this.roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public User id(BigDecimal id) {
        setId(id);
        return this;
    }

    public User email(String email) {
        setEmail(email);
        return this;
    }

    public User name(String name) {
        setName(name);
        return this;
    }

    public User lastName(String lastName) {
        setLastName(lastName);
        return this;
    }

    public User bio(String bio) {
        setBio(bio);
        return this;
    }

    public User areaOfInterest(String areaOfInterest) {
        setAreaOfInterest(areaOfInterest);
        return this;
    }

    public User orderHistories(Set<OrderHistory> orderHistories) {
        setOrderHistories(orderHistories);
        return this;
    }

    public User wishLists(Set<Wishlist> wishLists) {
        setWishLists(wishLists);
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof User)) {
            return false;
        }
        User user = (User) o;
        return Objects.equals(email, user.email) && Objects.equals(name, user.name) && Objects.equals(lastName, user.lastName) && Objects.equals(bio, user.bio) && Objects.equals(areaOfInterest, user.areaOfInterest);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, email, name, lastName, bio, areaOfInterest);
    }

    @Override
    public String toString() {
        return "{" +
            " id='" + getId() + "'" +
            ", email='" + getEmail() + "'" +
            ", name='" + getName() + "'" +
            ", lastName='" + getLastName() + "'" +
            ", bio='" + getBio() + "'" +
            ", areaOfInterest='" + getAreaOfInterest() + "'" +
            "}";
    }

}
