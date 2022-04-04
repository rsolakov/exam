package com.endava.exam.controller;

import com.endava.exam.dto.ItemDto;
import com.endava.exam.model.Item;
import com.endava.exam.service.ItemService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/items")
public class ItemController {

    private final ItemService itemService;
    private final ModelMapper modelMapper;

    public ItemController(ItemService itemService) {
        this.itemService = itemService;
        this.modelMapper = new ModelMapper();
    }

    @PostMapping
    ResponseEntity<ItemDto> createItem(@Valid @RequestBody ItemDto newItem) {
        Item item = modelMapper.map(newItem, Item.class);
        ItemDto mappedDto = modelMapper.map(itemService.createItem(item), ItemDto.class);
        return new ResponseEntity<>(mappedDto, HttpStatus.CREATED);
    }
}