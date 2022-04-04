package com.endava.exam.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class PurchaseDtoResponse extends PurchaseDto {

    private String change;
}