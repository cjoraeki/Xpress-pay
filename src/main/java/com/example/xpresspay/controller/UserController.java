package com.example.xpresspay.controller;

import com.example.xpresspay.dto.request.LoginDto;
import com.example.xpresspay.dto.request.UserRequestDto;
import com.example.xpresspay.dto.response.UserResponseDto;
import com.example.xpresspay.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/xpress-pay")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<UserResponseDto> signUp(@RequestBody UserRequestDto userDto) {
        UserResponseDto userResponseDto = userService.signUp(userDto);
        return new ResponseEntity<>(userResponseDto, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@RequestBody LoginDto loginDto) {
        try {
            String jwt = userService.authenticateUser(loginDto);
            return ResponseEntity.ok(jwt);
        } catch (Exception e) {
            return ResponseEntity.status(401).body("Authentication failed: " + e.getMessage());
        }
    }

    @GetMapping("/greet")
    public ResponseEntity<String> greet(String greet, HttpServletRequest request, HttpServletResponse response) {
        try {
            String greetingMessage = userService.hello(greet, request, response);
            return ResponseEntity.ok(greetingMessage);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error handling JWT");
        }
    }
}
