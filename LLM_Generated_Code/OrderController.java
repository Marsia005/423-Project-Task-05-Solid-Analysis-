package com.petstore.controller;

import com.petstore.model.Item;
import com.petstore.model.Order;
import com.petstore.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private ItemRepository itemRepository;

    @PostMapping("/checkout")
    public Order checkout(@RequestParam String username, @RequestBody Map<String, Integer> cartItems) {
        Order order = new Order();
        order.setUsername(username);
        order.setOrderDate(LocalDateTime.now());
        order.setItems(cartItems);
        order.setStatus("PLACED");

        BigDecimal total = BigDecimal.ZERO;
        for (Map.Entry<String, Integer> entry : cartItems.entrySet()) {
            Item item = itemRepository.findById(entry.getKey()).orElseThrow();
            int qty = entry.getValue();

            // reduce stock directly here
            item.setStockQuantity(item.getStockQuantity() - qty);
            itemRepository.save(item);

            total = total.add(item.getPrice().multiply(BigDecimal.valueOf(qty)));
        }
        order.setTotalPrice(total);

        return orderRepository.save(order);
    }

    @GetMapping("/{username}")
    public List<Order> getOrdersForUser(@PathVariable String username) {
        return orderRepository.findByUsername(username);
    }
}
