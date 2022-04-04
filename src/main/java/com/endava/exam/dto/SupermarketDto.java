package com.endava.exam.dto;

import com.endava.exam.messages.ValidationMessages;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
public class SupermarketDto {

    @Size(min = 3, max = 64, message = ValidationMessages.WRONG_NAME)
    private String name;

    @Size(min = 1, max = 25, message = ValidationMessages.WRONG_CITY)
    private String city;

    @Size(min = 1, max = 100, message = ValidationMessages.WRONG_STREET)
    private String street;

    @Size(min = 1, max = 3, message = ValidationMessages.WRONG_STREET_NUMBER)
    private String streetNumber;

    @Pattern(regexp = "^(([08]{2}[7-9]){1}\\d{7})?$", message = ValidationMessages.WRONG_PHONE_NUMBER)
    private String phoneNumber;

    @Pattern(regexp = "^(([0-1]\\d|2[0-3])(?::([0-5]\\d))?)-(([0-1]\\d|2[0-3])(?::([0-5]\\d))?)$", message =
            ValidationMessages.WRONG_WORK_HOURS)
    private String workHours;

}