package com.petstore.service;

import com.petstore.model.Cart;
import com.petstore.model.CartItem;
import com.petstore.model.Item;
import com.petstore.repository.ItemRepository;
import org.springframework.stereotype.Service;

@Service
public class CartService {

    private final ItemRepository itemRepository;

    public CartService(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    public Cart addItemToCart(Cart cart, String itemId, int quantity) {
        Item item = itemRepository.findById(itemId).orElseThrow();
        CartItem cartItem = new CartItem();
        cartItem.setItemId(itemId);
        cartItem.setQuantity(quantity);
        cartItem.setUnitPrice(item.getPrice());
        cart.addItem(cartItem);
        return cart;
    }

    public Cart removeItemFromCart(Cart cart, String itemId) {
        cart.getItems().removeIf(i -> i.getItemId().equals(itemId));
        return cart;
    }
}
