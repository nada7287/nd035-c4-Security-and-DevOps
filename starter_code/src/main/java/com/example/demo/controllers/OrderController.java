package com.example.demo.controllers;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.persistence.Cart;
import com.example.demo.model.persistence.User;
import com.example.demo.model.persistence.UserOrder;
import com.example.demo.model.persistence.repositories.CartRepository;
import com.example.demo.model.persistence.repositories.OrderRepository;
import com.example.demo.model.persistence.repositories.UserRepository;

@RestController
@RequestMapping("/api/order")
public class OrderController {
	private final static Logger logger= LoggerFactory.getLogger(OrderController.class);


	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private OrderRepository orderRepository;
	
	
	@PostMapping("/submit/{username}")
	public ResponseEntity<UserOrder> submit(@PathVariable String username) {
		User user = userRepository.findByUsername(username);
		if(user == null) {

			logger.error("*************************************************");
			logger.error("*************************************************");
			logger.error("User can't be found , no order can be created !!!");
			logger.error("*************************************************");
			logger.error("*************************************************");
			return ResponseEntity.notFound().build();
		}
		UserOrder order = UserOrder.createFromCart(user.getCart());
		orderRepository.save(order);
		logger.info("*************************************************");
		logger.info("*************************************************");
		logger.info("User is found and  order was successfuly created ");
		logger.info("*************************************************");
		logger.info("*************************************************");
		return ResponseEntity.ok(order);
	}
	
	@GetMapping("/history/{username}")
	public ResponseEntity<List<UserOrder>> getOrdersForUser(@PathVariable String username) {
		User user = userRepository.findByUsername(username);
		if(user == null) {

			logger.error("*************************************************");
			logger.error("*************************************************");
			logger.error("User can't be found and his order can't be found !!!");
			logger.error("*************************************************");
			logger.error("*************************************************");
			return ResponseEntity.notFound().build();
		}

		logger.info("*************************************************");
		logger.info("*************************************************");
		logger.info("User and his order were successfully found ");
		logger.info("*************************************************");
		logger.info("*************************************************");
		return ResponseEntity.ok(orderRepository.findByUser(user));
	}
}
