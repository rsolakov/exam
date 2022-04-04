package com.endava.exam.dto;

import com.endava.exam.messages.ValidationMessages;
import com.endava.exam.model.enums.Type;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

import static com.endava.exam.messages.ValidationMessages.TYPE_IS_NOT_PRESENT;

@Getter
@Setter
@NoArgsConstructor
public class ItemDto {

    @Size(min = 3, max = 64, message = ValidationMessages.WRONG_NAME)
    private String name;

    @Positive
    private Double price;

    @NotNull(message = TYPE_IS_NOT_PRESENT)
    private Type type;
}