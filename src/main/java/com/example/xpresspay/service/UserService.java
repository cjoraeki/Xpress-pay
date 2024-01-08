package com.example.xpresspay.service;

import com.example.xpresspay.dto.request.LoginDto;
import com.example.xpresspay.dto.request.UserRequestDto;
import com.example.xpresspay.dto.response.UserResponseDto;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public interface UserService {
    UserResponseDto signUp(UserRequestDto userRequestDto);
    String authenticateUser(LoginDto loginDto) throws Exception;
    String hello(String greet, HttpServletRequest request, HttpServletResponse response);
}
