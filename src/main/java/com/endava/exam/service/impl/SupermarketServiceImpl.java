package com.endava.exam.service.impl;

import com.endava.exam.dto.ItemDto;
import com.endava.exam.exceptions.ItemDoesNotExist;
import com.endava.exam.exceptions.SupermarketAlreadyExist;
import com.endava.exam.exceptions.SupermarketNotFoundException;
import com.endava.exam.model.Item;
import com.endava.exam.model.Supermarket;
import com.endava.exam.repository.ItemRepository;
import com.endava.exam.repository.SupermarketRepository;
import com.endava.exam.service.SupermarketService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.endava.exam.messages.ExceptionMessages.*;

@Service
public class SupermarketServiceImpl implements SupermarketService {

    private final SupermarketRepository supermarketRepository;
    private final ItemRepository itemRepository;

    public SupermarketServiceImpl(SupermarketRepository supermarketRepository, ItemRepository itemRepository) {
        this.supermarketRepository = supermarketRepository;
        this.itemRepository = itemRepository;
    }

    @Override
    public Supermarket createSupermarket(Supermarket supermarket) {
        if (supermarketRepository.existsByName(supermarket.getName())) {
            throw new SupermarketAlreadyExist(SUPERMARKET_HAS_ALREADY_EXIST);
        }
        return supermarketRepository.save(supermarket);
    }

    @Override
    public Supermarket findByName(String name) {

        Optional<Supermarket> supermarket = supermarketRepository.findByName(name);
        if (supermarket.isEmpty()) {
            throw new SupermarketNotFoundException(SUPERMARKET_NOT_FOUND);
        }
        return supermarket.get();
    }

    @Override
    public Supermarket addItemsToSupermarket(String name, List<ItemDto> items) {

        Supermarket supermarket = new Supermarket();

        try {
            supermarket = supermarketRepository.getSupermarketByName(name);
        } catch (SupermarketNotFoundException ex) {
            throw new SupermarketNotFoundException(SUPERMARKET_NOT_FOUND);
        }

        supermarket.setItems(getItems(items));

        return supermarketRepository.save(supermarket);
    }

    private List<Item> getItems(List<ItemDto> items) {
        List<Item> listOfItems = new ArrayList<>();
        for (ItemDto itemDto : items) {
            Item item = itemRepository.findByName(itemDto.getName()).orElseThrow(() -> {
                throw new ItemDoesNotExist(ITEM_DOES_NOT_EXIST);
            });
            listOfItems.add(item);
        }
        return listOfItems;
    }
}