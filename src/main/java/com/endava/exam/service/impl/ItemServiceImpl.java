package com.endava.exam.service.impl;

import com.endava.exam.model.Item;
import com.endava.exam.repository.ItemRepository;
import com.endava.exam.service.ItemService;
import org.springframework.stereotype.Service;

@Service
public class ItemServiceImpl implements ItemService {

    private final ItemRepository itemRepository;

    public ItemServiceImpl(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    @Override
    public Item createItem(Item item) {

        return itemRepository.save(item);
    }
}