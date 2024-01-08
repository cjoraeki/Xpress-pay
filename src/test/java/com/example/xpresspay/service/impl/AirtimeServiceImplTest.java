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

        // Set private fields using ReflectionTestUtils
        ReflectionTestUtils.setField(airtimeService, "PUBLIC_KEY", "your-public-key");
        ReflectionTestUtils.setField(airtimeService, "PRIVATE_KEY", "your-private-key");
        ReflectionTestUtils.setField(airtimeService, "apiUrl", "your-api-url");
    }

    @Test
    void topUpAirtime() throws JsonProcessingException {
        // Given
        AirtimeRequestDto airtimeRequestDto = new AirtimeRequestDto();
        // Set other properties of airtimeRequestDto

        // Mock the response
        AirtimeResponseDto mockResponse = new AirtimeResponseDto();
        // Set properties of mockResponse

        // Mock the restTemplate.exchange method
        ResponseEntity<AirtimeResponseDto> responseEntity = new ResponseEntity<>(mockResponse, HttpStatus.OK);
        when(restTemplate.exchange(any(String.class), any(HttpMethod.class), any(HttpEntity.class), any(Class.class)))
                .thenReturn(responseEntity);

        // When
        AirtimeResponseDto result = airtimeService.topUpAirtime(airtimeRequestDto);

        // Then
        assertEquals(mockResponse, result);
    }
}
