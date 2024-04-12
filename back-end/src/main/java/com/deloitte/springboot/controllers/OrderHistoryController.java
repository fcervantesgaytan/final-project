package com.deloitte.springboot.controllers;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.deloitte.springboot.entities.OrderHistory;
import com.deloitte.springboot.entities.User;
import com.deloitte.springboot.repositories.OrderHistoryRepository;
import com.deloitte.springboot.repositories.UserRepository;

@RestController
@RequestMapping("/api")
public class OrderHistoryController {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private OrderHistoryRepository orderHistoryRepository;

    @GetMapping("/orderhistories/{userId}")
    public List<OrderHistory> getOrderHistoriesByUser(@PathVariable BigDecimal userId) {
        User user = userRepository.findById(userId).get();
        List<OrderHistory> orderHistories = orderHistoryRepository.findByUser(user);

        return orderHistories;
    }
}
