package com.deloitte.springboot.repositories;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.deloitte.springboot.entities.User;

@Repository
public interface UserRepository extends JpaRepository<User, BigDecimal> {
    List<User> findByNameAndLastName(String name, String lastName);
    Optional<User> findByEmail(String email);
    boolean existsByNameAndLastName(String name, String lastName);
    boolean existsByEmail(String email);
}
