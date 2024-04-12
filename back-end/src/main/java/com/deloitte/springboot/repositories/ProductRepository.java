package com.deloitte.springboot.repositories;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.deloitte.springboot.entities.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, BigDecimal> {
    Optional<Product> findByName(String name);
    List<Product> findByPriceBetween(BigDecimal minPrice, BigDecimal maxPrice);
    boolean existsByName(String name);
    boolean existsByPriceBetween(BigDecimal minPrice, BigDecimal maxPrice);
}
