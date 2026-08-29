package com.petstore.controller;

import com.petstore.model.Item;
import com.petstore.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/catalog")
public class CatalogController {

    @Autowired
    private ItemRepository itemRepository;

    @GetMapping("/items")
    public List<Item> browseAll() {
        return itemRepository.findAll();
    }

    @GetMapping("/item/{itemId}")
    public Item getItem(@PathVariable String itemId) {
        return itemRepository.findById(itemId).orElseThrow();
    }
}
