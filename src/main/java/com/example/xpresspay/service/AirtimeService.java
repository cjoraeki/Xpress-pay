package com.example.xpresspay.service;

import com.example.xpresspay.dto.request.AirtimeRequestDto;
import com.example.xpresspay.dto.response.AirtimeResponseDto;
import com.fasterxml.jackson.core.JsonProcessingException;

public interface AirtimeService {
    AirtimeResponseDto topUpAirtime(AirtimeRequestDto airtimeRequestDto) throws JsonProcessingException;
}
