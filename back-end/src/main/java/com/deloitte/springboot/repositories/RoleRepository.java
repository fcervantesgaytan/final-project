package com.deloitte.springboot.repositories;

import java.math.BigDecimal;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.deloitte.springboot.entities.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, BigDecimal> {
    Optional<Role> findByName(String name);
}
