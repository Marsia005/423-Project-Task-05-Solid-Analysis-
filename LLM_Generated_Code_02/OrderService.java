package com.petstore.service;

import com.petstore.model.Item;
import com.petstore.model.Order;
import com.petstore.repository.ItemRepository;
import com.petstore.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private ItemRepository itemRepository;

    public Order checkout(String username, Map<String, Integer> cartItems) {
        Order order = new Order();
        order.setUsername(username);
        order.setOrderDate(LocalDateTime.now());
        order.setItems(cartItems);
        order.setStatus("PLACED");

        BigDecimal total = BigDecimal.ZERO;
        for (Map.Entry<String, Integer> entry : cartItems.entrySet()) {
            Item item = itemRepository.findById(entry.getKey()).orElseThrow();
            int qty = entry.getValue();
            item.setStockQuantity(item.getStockQuantity() - qty);
            itemRepository.save(item);
            total = total.add(item.getPrice().multiply(BigDecimal.valueOf(qty)));
        }
        order.setTotalPrice(total);
        return orderRepository.save(order);
    }

    public List<Order> getOrdersForUser(String username) {
        return orderRepository.findByUsername(username);
    }
}
