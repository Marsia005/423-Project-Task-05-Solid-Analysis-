package com.petstore.controller;

import com.petstore.model.Order;
import com.petstore.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping("/checkout")
    public Order checkout(@RequestParam String username, @RequestBody Map<String, Integer> cartItems) {
        return orderService.checkout(username, cartItems);
    }

    @GetMapping("/{username}")
    public List<Order> getOrdersForUser(@PathVariable String username) {
        return orderService.getOrdersForUser(username);
    }
}
