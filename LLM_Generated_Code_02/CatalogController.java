package com.petstore.controller;

import com.petstore.model.Item;
import com.petstore.service.CatalogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/catalog")
public class CatalogController {

    @Autowired
    private CatalogService catalogService;

    @GetMapping("/items")
    public List<Item> browseAll() {
        return catalogService.browseAll();
    }

    @GetMapping("/item/{itemId}")
    public Item getItem(@PathVariable String itemId) {
        return catalogService.getItem(itemId);
    }
}
