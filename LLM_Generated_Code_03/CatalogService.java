package com.petstore.service;

import com.petstore.model.Item;
import com.petstore.repository.ItemRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CatalogService {

    private final ItemRepository itemRepository;

    public CatalogService(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    public List<Item> browseByCategory(String categoryId) {
        return itemRepository.findByCategoryId(categoryId);
    }

    public Item getItem(String itemId) {
        return itemRepository.findById(itemId).orElseThrow();
    }

    public List<Item> search(String keyword) {
        return itemRepository.findByNameContaining(keyword);
    }
}
