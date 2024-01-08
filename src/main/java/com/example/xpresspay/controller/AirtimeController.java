package com.example.xpresspay.controller;

import com.example.xpresspay.dto.request.AirtimeRequestDto;
import com.example.xpresspay.dto.request.UserRequestDto;
import com.example.xpresspay.dto.response.AirtimeResponseDto;
import com.example.xpresspay.dto.response.UserResponseDto;
import com.example.xpresspay.service.AirtimeService;
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

//    @PostMapping("/airtime")
//    public ResponseEntity<UserResponseDto> signUp(@RequestBody AirtimeRequestDto airtimeRequestDto) {
//        AirtimeResponseDto airtimeResponseDto = airtimeService.();
//        return new ResponseEntity<>(airtimeResponseDto, );
//    }
}
