package com.petstore.controller;

import com.petstore.model.Cart;
import com.petstore.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cart")
public class CartController {

    @Autowired
    private CartService cartService;

    @PostMapping("/add")
    public Cart addItem(@RequestBody Cart cart, @RequestParam String itemId) {
        return cartService.addItem(cart, itemId);
    }
}
