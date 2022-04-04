package com.endava.exam.controller;

import com.endava.exam.dto.AddItemsToSupermarketDto;
import com.endava.exam.dto.GetSupermarketResponse;
import com.endava.exam.dto.SupermarketDto;
import com.endava.exam.model.Supermarket;
import com.endava.exam.service.SupermarketService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/supermarkets")
public class SupermarketController {

    private final SupermarketService supermarketService;
    private final ModelMapper modelMapper;

    public SupermarketController(SupermarketService supermarketService) {
        this.supermarketService = supermarketService;
        this.modelMapper = new ModelMapper();
    }

    @PostMapping
    ResponseEntity<SupermarketDto> createSupermarket(@Valid @RequestBody SupermarketDto newSupermarket) {

        Supermarket supermarket = modelMapper.map(newSupermarket, Supermarket.class);
        SupermarketDto mappedSupermarket = modelMapper.map(supermarketService.createSupermarket(supermarket),
                SupermarketDto.class);
        return new ResponseEntity<>(mappedSupermarket, HttpStatus.CREATED);
    }

    @PostMapping("/add")
    ResponseEntity<AddItemsToSupermarketDto> addItemsToSupermarket(@Valid @RequestBody AddItemsToSupermarketDto addItemsToSupermarket) {

        Supermarket supermarket = modelMapper.map(addItemsToSupermarket, Supermarket.class);
        AddItemsToSupermarketDto mappedDto =
                modelMapper.map(supermarketService.addItemsToSupermarket(supermarket.getName(),
                                addItemsToSupermarket.getItems()),
                        AddItemsToSupermarketDto.class);

        return new ResponseEntity<>(mappedDto, HttpStatus.OK);
    }

    @GetMapping("/{name}")
    ResponseEntity<GetSupermarketResponse> getSupermarketByName(@PathVariable(value = "name") String name) {

        GetSupermarketResponse mappedDto = modelMapper.map(supermarketService.findByName(name),
                GetSupermarketResponse.class);
        return ResponseEntity.ok(mappedDto);
    }

}