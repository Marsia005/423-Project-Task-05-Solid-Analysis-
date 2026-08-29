package com.petstore.controller;

import com.petstore.model.Order;
import com.petstore.service.OrderService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/checkout")
    public Order checkout(@RequestParam String username, @RequestBody Map<String, Integer> cartItems) {
        return orderService.checkout(username, cartItems);
    }

    @GetMapping("/{username}")
    public List<Order> getOrdersForUser(@PathVariable String username) {
        return orderService.getOrdersForUser(username);
    }
}
