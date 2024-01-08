package com.example.xpresspay.service.impl;

import com.example.xpresspay.dto.request.AirtimeRequestDto;
import com.example.xpresspay.dto.response.AirtimeResponseDto;
import com.example.xpresspay.dto.request.Airtime;
import com.example.xpresspay.dto.request.VTURequest;
import com.example.xpresspay.service.AirtimeService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import org.apache.commons.codec.binary.Hex;



@Service
@RequiredArgsConstructor
@Slf4j
public class AirtimeServiceImpl implements AirtimeService {

    private final RestTemplate restTemplate;

    @Value("${spring.xpress.payments.api.public-key}")
    private String PUBLIC_KEY;

    @Value("${spring.xpress.payments.api.private-key}")
    private String PRIVATE_KEY;

    @Value("${spring.xpress.payments.api.url}")
    private String apiUrl;

    @Override
    public AirtimeResponseDto topUpAirtime(AirtimeRequestDto airtimeRequestDto) throws JsonProcessingException {

        Airtime airtime = new Airtime();
        airtime.setPhoneNumber(airtimeRequestDto.getPhoneNumber());
        airtime.setAmount(airtimeRequestDto.getAmount());

        VTURequest vtuRequest = new VTURequest();
        vtuRequest.setUniqueCode("MTN_24207");
        vtuRequest.setRequestId(airtimeRequestDto.getRequestId());
        vtuRequest.setDetails(airtime);

        ObjectMapper objectMapper = new ObjectMapper();
        String data = objectMapper.writeValueAsString(vtuRequest);
        System.out.println(data);
        String privateKey = PRIVATE_KEY;

        String generatedHMAC = calculateHMAC512(data, privateKey);

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + PUBLIC_KEY);
        headers.set("PaymentHash", generatedHMAC);
        headers.set("Channel", "API");
        headers.setContentType(MediaType.APPLICATION_JSON);

        log.info("Generated HMAC: " + generatedHMAC);

        HttpEntity<VTURequest> entity = new HttpEntity<>(vtuRequest, headers);

        ResponseEntity<AirtimeResponseDto> response = restTemplate.exchange(apiUrl, HttpMethod.POST, entity, AirtimeResponseDto.class);

        return response.getBody();
    }


    public static String calculateHMAC512(String data, String key) {

        String HMAC_SHA512 = "HmacSHA512";

        SecretKeySpec secretKeySpec = new SecretKeySpec(key.getBytes(), HMAC_SHA512);

        Mac mac = null;

        try {

            mac = Mac.getInstance(HMAC_SHA512);

            mac.init(secretKeySpec);

            return Hex.encodeHexString(mac.doFinal(data.getBytes()));

        } catch (NoSuchAlgorithmException | InvalidKeyException e) {

            e.printStackTrace();

            throw new RuntimeException(e.getMessage());

        }

    }

}