package com.petstore.controller;

import com.petstore.model.Item;
import com.petstore.service.CatalogService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/catalog")
public class CatalogController {

    private final CatalogService catalogService;

    public CatalogController(CatalogService catalogService) {
        this.catalogService = catalogService;
    }

    @GetMapping("/category/{categoryId}")
    public List<Item> browseByCategory(@PathVariable String categoryId) {
        return catalogService.browseByCategory(categoryId);
    }

    @GetMapping("/item/{itemId}")
    public Item getItem(@PathVariable String itemId) {
        return catalogService.getItem(itemId);
    }

    @GetMapping("/search")
    public List<Item> search(@RequestParam String keyword) {
        return catalogService.search(keyword);
    }
}
