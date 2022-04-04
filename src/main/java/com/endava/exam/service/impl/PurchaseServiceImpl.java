package com.endava.exam.service.impl;

import com.endava.exam.dto.ItemDto;
import com.endava.exam.dto.PurchaseDtoResponse;
import com.endava.exam.exceptions.ItemDoesNotExist;
import com.endava.exam.exceptions.PurchaseNotFoundException;
import com.endava.exam.exceptions.SupermarketNotFoundException;
import com.endava.exam.messages.ExceptionMessages;
import com.endava.exam.model.Item;
import com.endava.exam.model.Purchase;
import com.endava.exam.model.Supermarket;
import com.endava.exam.repository.ItemRepository;
import com.endava.exam.repository.PurchaseRepository;
import com.endava.exam.repository.SupermarketRepository;
import com.endava.exam.service.PurchaseService;
import com.sipios.springsearch.anotation.SearchSpec;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.endava.exam.messages.ExceptionMessages.ITEM_DOES_NOT_EXIST;
import static com.endava.exam.messages.ExceptionMessages.SUPERMARKET_NOT_FOUND;

@Service
public class PurchaseServiceImpl implements PurchaseService {

    private final PurchaseRepository purchaseRepository;
    private final SupermarketRepository supermarketRepository;
    private final ItemRepository itemRepository;
    private final ModelMapper modelMapper;

    public PurchaseServiceImpl(PurchaseRepository purchaseRepository, SupermarketRepository supermarketRepository, ItemRepository itemRepository) {
        this.purchaseRepository = purchaseRepository;
        this.supermarketRepository = supermarketRepository;
        this.itemRepository = itemRepository;
        this.modelMapper = new ModelMapper();
    }

    @Override
    public Purchase buyItemsFromSupermarket(String name, Purchase purchase, List<ItemDto> items) {

        Supermarket supermarket = supermarketRepository.findByName(name).orElseThrow(() -> {
            throw new SupermarketNotFoundException(SUPERMARKET_NOT_FOUND);
        });

        purchase.setSupermarket(supermarket);
        purchase.setItems(getItems(items));

        return purchaseRepository.save(purchase);
    }

    @Override
    public Page<PurchaseDtoResponse> getPurchases(@SearchSpec Specification<Purchase> specification, Pageable pageable) {

        Page<Purchase> all = purchaseRepository.findAll(Specification.where(specification), pageable);
        Page<PurchaseDtoResponse> responses = new PageImpl<>(all.stream()
                .map(purchase -> modelMapper.map(purchase, PurchaseDtoResponse.class)).collect(Collectors.toList()));
        if (responses.isEmpty()) {
            throw new PurchaseNotFoundException(ExceptionMessages.PURCHASE_NOT_FOUND);
        }
        return responses;
    }

    @Override
    public void exportPurchasesAsCSV(Specification<Purchase> specification, Writer writer, Pageable pageable) throws IOException {

        Page<Purchase> purchases = purchaseRepository.findAll(Specification.where(specification), pageable);
        try (CSVPrinter csvPrinter = new CSVPrinter(writer, CSVFormat.DEFAULT)) {
            for (Purchase purchase : purchases) {
                csvPrinter.printRecord(purchase.getSupermarket().getName(), purchase.getItems().toString(),
                        purchase.getCashAmount(),
                        purchase.getPaymentType());
            }
        } catch (IOException exception) {
            throw new IOException(ExceptionMessages.THE_FILE_CANNOT_BE_EXPORTED);
        }
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