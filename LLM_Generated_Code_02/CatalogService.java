package com.petstore.service;

import com.petstore.model.Item;
import com.petstore.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CatalogService {

    @Autowired
    private ItemRepository itemRepository;

    public List<Item> browseAll() {
        return itemRepository.findAll();
    }

    public Item getItem(String itemId) {
        return itemRepository.findById(itemId).orElseThrow();
    }
}
