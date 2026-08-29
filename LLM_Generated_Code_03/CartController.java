package com.petstore.controller;

import com.petstore.model.Cart;
import com.petstore.service.CartService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cart")
public class CartController {

    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @PostMapping("/add")
    public Cart addItem(@RequestBody Cart cart, @RequestParam String itemId, @RequestParam int quantity) {
        return cartService.addItemToCart(cart, itemId, quantity);
    }

    @PostMapping("/remove")
    public Cart removeItem(@RequestBody Cart cart, @RequestParam String itemId) {
        return cartService.removeItemFromCart(cart, itemId);
    }
}
