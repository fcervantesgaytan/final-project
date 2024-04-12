package com.deloitte.springboot.repositories;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.deloitte.springboot.entities.Product;
import com.deloitte.springboot.entities.User;
import com.deloitte.springboot.entities.Wishlist;

@Repository
public interface WishlistRepository extends JpaRepository<Wishlist, BigDecimal> {
    Optional<Wishlist> findByUserAndProduct(User user, Product product);
    boolean existsByUserIdAndProductId(BigDecimal userId, BigDecimal productId);
    Optional<Wishlist> findByUserIdAndProductId(BigDecimal userId, BigDecimal productId);
    List<Wishlist> findByUser(User user);
}
