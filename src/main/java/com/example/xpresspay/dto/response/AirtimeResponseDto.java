package com.example.xpresspay.dto.response;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class AirtimeResponseDto {
    private Long requestId;
    private String referenceId;
    private String responseCode;
    private String responseMessage;
    private String data;
}
