package com.deloitte.springboot.entities;

import java.math.BigDecimal;

import org.hibernate.annotations.GenericGenerator;

import com.deloitte.springboot.generators.CustomGenerator;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.util.Objects;

@Entity
@Table(name = "ROLES")
public class Role {
    @Id
    @GenericGenerator(name = "custom_id", type = CustomGenerator.class)
    @GeneratedValue(generator = "custom_id")
    @Column(name = "ROLE_ID")
    private BigDecimal id;
    @Column(name = "NAME")
    private String name;

    public Role() { }

    public Role(String name) {
        this.name = name;
    }

    public Role(BigDecimal id, String name) {
        this.id = id;
        this.name = name;
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

    public Role id(BigDecimal id) {
        setId(id);
        return this;
    }

    public Role name(String name) {
        setName(name);
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof Role)) {
            return false;
        }
        Role role = (Role) o;
        return Objects.equals(id, role.id) && Objects.equals(name, role.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }

    @Override
    public String toString() {
        return "{" +
            " id='" + getId() + "'" +
            ", name='" + getName() + "'" +
            "}";
    }    
}
