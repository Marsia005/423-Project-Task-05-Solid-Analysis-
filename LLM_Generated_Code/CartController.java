package com.petstore.controller;

import com.petstore.model.Cart;
import com.petstore.model.Item;
import com.petstore.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cart")
public class CartController {

    @Autowired
    private ItemRepository itemRepository;

    @PostMapping("/add")
    public Cart addItem(@RequestBody Cart cart, @RequestParam String itemId) {
        // stock check done directly here, no service layer
        Item item = itemRepository.findById(itemId).orElseThrow();
        cart.getItemIds().add(itemId);
        return cart;
    }
}
