package com.deloitte.springboot.repositories;

import java.math.BigDecimal;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.deloitte.springboot.entities.OrderHistory;
import com.deloitte.springboot.entities.User;

@Repository
public interface OrderHistoryRepository extends JpaRepository<OrderHistory, BigDecimal> {
    List<OrderHistory> findByUser(User user);
}
