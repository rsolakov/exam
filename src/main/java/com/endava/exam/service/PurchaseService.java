package com.endava.exam.service;

import com.endava.exam.dto.ItemDto;
import com.endava.exam.dto.PurchaseDtoResponse;
import com.endava.exam.model.Purchase;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.io.IOException;
import java.io.Writer;
import java.util.List;

public interface PurchaseService {

    Purchase buyItemsFromSupermarket(String name, Purchase purchase, List<ItemDto> items);

    Page<PurchaseDtoResponse> getPurchases(Specification<Purchase> specification, Pageable pageable);

    void exportPurchasesAsCSV(Specification<Purchase> specification, Writer writer, Pageable pageable) throws IOException;
}