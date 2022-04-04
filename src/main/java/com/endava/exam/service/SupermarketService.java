package com.endava.exam.service;

import com.endava.exam.dto.ItemDto;
import com.endava.exam.model.Supermarket;

import java.util.List;

public interface SupermarketService {

    Supermarket createSupermarket(Supermarket supermarket);

    Supermarket findByName(String name);

    Supermarket addItemsToSupermarket(String name, List<ItemDto> items);

}