package com.endava.exam.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class GetSupermarketResponse extends SupermarketDto {

    private List<ItemDto> items;
}