package com.deloitte.springboot.entities;

import java.math.BigDecimal;
import java.sql.Timestamp;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.CreatedDate;

import com.deloitte.springboot.generators.CustomGenerator;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "ORDER_HISTORY")
public class OrderHistory {
    @Id
    @GenericGenerator(name = "custom_id", type = CustomGenerator.class)
    @GeneratedValue(generator = "custom_id")
    @Column(name = "ORDER_ID")
    private BigDecimal id;
    @Temporal(TemporalType.TIMESTAMP)
    @CreatedDate
    @Column(name = "ORDER_DATE")
    private Timestamp orderDate = new Timestamp(new Date().getTime());
    @ManyToOne
    @JoinColumn(name = "USER_ID")
    private User user;
    @ManyToOne
    @JoinColumn(name = "PRODUCT_ID")
    private Product product;

    public OrderHistory() { }

    public OrderHistory(User user, Product product) {
        this.user = user;
        this.product = product;
    }

    public BigDecimal getId() {
        return this.id;
    }

    public void setId(BigDecimal id) {
        this.id = id;
    }

    public Timestamp getOrderDate() {
        return this.orderDate;
    }

    public void setOrderDate(Timestamp orderDate) {
        this.orderDate = orderDate;
    }

    @JsonIgnore
    public User getUser() {
        return this.user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Product getProduct() {
        return this.product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public OrderHistory id(BigDecimal id) {
        setId(id);
        return this;
    }

    public OrderHistory orderDate(Timestamp orderDate) {
        setOrderDate(orderDate);
        return this;
    }

    public OrderHistory user(User user) {
        setUser(user);
        return this;
    }

    public OrderHistory product(Product product) {
        setProduct(product);
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof OrderHistory)) {
            return false;
        }
        OrderHistory orderHistory = (OrderHistory) o;
        return Objects.equals(orderDate, orderHistory.orderDate) && Objects.equals(user, orderHistory.user) && Objects.equals(product, orderHistory.product);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, orderDate, user, product);
    }

    @Override
    public String toString() {
        return "{" +
            " id='" + getId() + "'" +
            ", orderDate='" + getOrderDate() + "'" +
            ", user='" + getUser() + "'" +
            ", product='" + getProduct() + "'" +
            "}";
    }    
}
