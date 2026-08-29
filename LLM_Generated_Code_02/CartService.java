package com.petstore.service;

import com.petstore.model.Cart;
import com.petstore.model.Item;
import com.petstore.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CartService {

    @Autowired
    private ItemRepository itemRepository;

    public Cart addItem(Cart cart, String itemId) {
        Item item = itemRepository.findById(itemId).orElseThrow();
        cart.getItemIds().add(itemId);
        return cart;
    }
}
