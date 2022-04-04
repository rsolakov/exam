package com.endava.exam.controller;

import com.endava.exam.dto.PurchaseDto;
import com.endava.exam.dto.PurchaseDtoResponse;
import com.endava.exam.model.Purchase;
import com.endava.exam.service.PurchaseService;
import com.sipios.springsearch.anotation.SearchSpec;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;

@RestController
@RequestMapping("/purchases")
public class PurchaseController {

    private final PurchaseService purchaseService;
    private final ModelMapper modelMapper;

    public PurchaseController(PurchaseService purchaseService) {
        this.purchaseService = purchaseService;
        this.modelMapper = new ModelMapper();
    }

    @PostMapping
    ResponseEntity<PurchaseDtoResponse> buyItemsFromSupermarket(@Valid @RequestBody PurchaseDto purchaseDto) {

        Purchase purchase = modelMapper.map(purchaseDto, Purchase.class);
        PurchaseDtoResponse mappedPurchase =
                modelMapper.map(purchaseService.buyItemsFromSupermarket(purchaseDto.getName(), purchase,
                                purchaseDto.getItems()),
                        PurchaseDtoResponse.class);
        return new ResponseEntity<>(mappedPurchase, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<Page<PurchaseDtoResponse>> getPurchases(@SearchSpec Specification<Purchase> specification,
                                                                  Pageable pageable) {

        return ResponseEntity.ok(purchaseService.getPurchases(Specification.where(specification), pageable));
    }


    @GetMapping("/export")
    public void exportPurchasesAsCSV(HttpServletResponse servletResponse, @SearchSpec Specification<Purchase> specification,
                                     Pageable pageable) throws IOException {

        servletResponse.setContentType("text/csv");
        servletResponse.addHeader("Content-Disposition", "attachment; filename=\"purchases.csv\"");
        purchaseService.exportPurchasesAsCSV(specification, servletResponse.getWriter(), pageable);
    }

}