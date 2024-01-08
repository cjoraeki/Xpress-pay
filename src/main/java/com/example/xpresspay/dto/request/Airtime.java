package com.example.xpresspay.dto.request;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
@ToString
public class Airtime {
    private String phoneNumber; //Testing phone number = 08033333333
    private int amount;
}
