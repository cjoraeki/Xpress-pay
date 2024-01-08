package com.example.xpresspay.service.impl;


import com.example.xpresspay.dto.request.AirtimeRequestDto;
import com.example.xpresspay.dto.response.AirtimeResponseDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.*;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
//import static when;

class AirtimeServiceImplTest {

    @InjectMocks
    private AirtimeServiceImpl airtimeService;

    @Mock
    private RestTemplate restTemplate;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        ReflectionTestUtils.setField(airtimeService, "PUBLIC_KEY", "1234567890");
        ReflectionTestUtils.setField(airtimeService, "PRIVATE_KEY", "0987654321");
        ReflectionTestUtils.setField(airtimeService, "apiUrl", "http://localhost:8080");
    }

    @Test
    void topUpAirtime() throws JsonProcessingException {

        AirtimeRequestDto airtimeRequestDto = new AirtimeRequestDto();
        AirtimeResponseDto mockResponse = new AirtimeResponseDto();

        ResponseEntity<AirtimeResponseDto> responseEntity = new ResponseEntity<>(mockResponse, HttpStatus.OK);
        when(restTemplate.exchange(any(String.class), any(HttpMethod.class), any(HttpEntity.class), any(Class.class)))
                .thenReturn(responseEntity);

        AirtimeResponseDto result = airtimeService.topUpAirtime(airtimeRequestDto);

        assertEquals(mockResponse, result);
    }
}
