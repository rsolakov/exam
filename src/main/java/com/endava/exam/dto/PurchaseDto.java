package com.endava.exam.dto;

import com.endava.exam.model.enums.PaymentType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import java.util.List;

import static com.endava.exam.messages.ValidationMessages.PAYMENT_TYPE_IS_NOT_PRESENT;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class PurchaseDto {

    private String name;

    private List<ItemDto> items;

    @NotNull(message = PAYMENT_TYPE_IS_NOT_PRESENT)
    private PaymentType paymentType;

    private Double cashAmount;
}