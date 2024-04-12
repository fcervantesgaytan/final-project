package com.deloitte.springboot;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.jdbc.JdbcTestUtils;

import com.deloitte.springboot.repositories.OrderHistoryRepository;
import com.deloitte.springboot.repositories.ProductRepository;
import com.deloitte.springboot.repositories.UserRepository;

@SpringBootTest
class SpringBootMainApplicationTests {
	@Autowired
	UserRepository userRepository;
	@Autowired
	OrderHistoryRepository orderHistoryRepository;
	@Autowired
	ProductRepository productRepository;
	@Autowired
    JdbcTemplate jdbcTemplate;

	@BeforeEach
	public void setUp() {
		JdbcTestUtils.deleteFromTables(jdbcTemplate, "ORDER_HISTORY", "PRODUCTS", "USERS");
	}

	@Test
	void contextLoads() { }
}
