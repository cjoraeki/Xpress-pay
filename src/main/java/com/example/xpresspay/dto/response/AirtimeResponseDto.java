package com.example.xpresspay.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AirtimeResponseDto {
    private String requestId;
    private String referenceId;
    private String responseCode;
    private String responseMessage;
    private Object data;
}
