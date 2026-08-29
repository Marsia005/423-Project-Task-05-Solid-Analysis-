package com.petstore.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class Cart {

    private String username;
    private List<CartItem> items = new ArrayList<>();

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public List<CartItem> getItems() { return items; }
    public void setItems(List<CartItem> items) { this.items = items; }

    public void addItem(CartItem item) {
        items.add(item);
    }

    public BigDecimal getSubTotal() {
        return items.stream()
            .map(CartItem::calculateTotal)
            .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
