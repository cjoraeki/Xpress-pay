package com.example.xpresspay.service.impl;

import com.example.xpresspay.dto.request.LoginDto;
import com.example.xpresspay.dto.request.UserRequestDto;
import com.example.xpresspay.dto.response.UserResponseDto;
import com.example.xpresspay.entity.User;
import com.example.xpresspay.repository.UserRepository;
import com.example.xpresspay.utils.JWTAuthFilter;
import com.example.xpresspay.utils.JWTUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class UserServiceImplTest {

    @InjectMocks
    private UserServiceImpl userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private JWTUtils jwtUtils;

    @Mock
    private JWTAuthFilter jwtAuthFilter;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void signUp() {

        UserRequestDto userRequestDto = new UserRequestDto();

        User user = new User();
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.map(userRequestDto, user);

        when(userRepository.save(user)).thenReturn(user);

        UserResponseDto result = userService.signUp(userRequestDto);

        assertNotNull(result);
    }

    @Test
    void authenticateUser() throws Exception {

        LoginDto loginDto = new LoginDto();

        User user = new User();
        when(userRepository.findByEmail(loginDto.getEmail())).thenReturn(Optional.of(user));
        when(jwtUtils.generateToken(user)).thenReturn("mocked-jwt-token");

        String result = userService.authenticateUser(loginDto);

        assertNotNull(result);
        assertEquals("mocked-jwt-token", result);
    }

    @Test
    void hello_authenticatedUser() {
        HttpServletRequest mockRequest = mock(HttpServletRequest.class);
        HttpServletResponse mockResponse = mock(HttpServletResponse.class);
        when(jwtAuthFilter.doFilter(mockRequest, mockResponse)).thenReturn(HttpServletResponse.SC_ACCEPTED);

        String result = userService.hello("Greetings", mockRequest, mockResponse);

        assertEquals("Hello Pay", result);
    }

    @Test
    void hello_unauthenticatedUser() {
        HttpServletRequest mockRequest = mock(HttpServletRequest.class);
        HttpServletResponse mockResponse = mock(HttpServletResponse.class);
        when(jwtAuthFilter.doFilter(mockRequest, mockResponse)).thenThrow(new RuntimeException("Error handling JWT"));

        assertThrows(RuntimeException.class, () -> userService.hello("Greetings", mockRequest, mockResponse));
    }
}
