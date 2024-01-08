package com.example.xpresspay.dto.request;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class AirtimeRequestDto {
    private String phoneNumber; //Testing phone number = 08033333333
    private int amount;
    private String requestId;

}
