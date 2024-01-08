package com.example.xpresspay.controller;

import com.example.xpresspay.dto.request.AirtimeRequestDto;
import com.example.xpresspay.dto.response.AirtimeResponseDto;
import com.example.xpresspay.service.AirtimeService;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/xpress-pay")
@RequiredArgsConstructor
public class AirtimeController {

    private final AirtimeService airtimeService;

    @PostMapping("/airtime/fulfil")
    public ResponseEntity<AirtimeResponseDto> fulfilAirtime(@RequestBody AirtimeRequestDto airtimeRequestDto) throws JsonProcessingException {
        AirtimeResponseDto airtimeResponseDto = airtimeService.topUpAirtime(airtimeRequestDto);
        return new ResponseEntity<>(airtimeResponseDto, HttpStatus.CREATED);
    }
}
