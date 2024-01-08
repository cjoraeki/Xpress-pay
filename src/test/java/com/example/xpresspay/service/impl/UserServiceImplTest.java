package com.example.xpresspay.service.impl;

import com.example.xpresspay.dto.request.LoginDto;
import com.example.xpresspay.dto.request.UserRequestDto;
import com.example.xpresspay.dto.response.UserResponseDto;
import com.example.xpresspay.entity.User;
import com.example.xpresspay.repository.UserRepository;
import com.example.xpresspay.utils.JWTAuthFilter;
import com.example.xpresspay.utils.JWTUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
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
        // Given
        UserRequestDto userRequestDto = new UserRequestDto();
        // Set properties of userRequestDto

        User user = new User();
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.map(userRequestDto, user);

        when(userRepository.save(user)).thenReturn(user);

        // When
        UserResponseDto result = userService.signUp(userRequestDto);

        // Then
        assertNotNull(result);
        // Add more assertions as needed
    }

    @Test
    void authenticateUser() throws Exception {
        // Given
        LoginDto loginDto = new LoginDto();
        // Set properties of loginDto

        User user = new User();
        when(userRepository.findByEmail(loginDto.getEmail())).thenReturn(Optional.of(user));
        when(jwtUtils.generateToken(user)).thenReturn("mocked-jwt-token");

        // When
        String result = userService.authenticateUser(loginDto);

        // Then
        assertNotNull(result);
        assertEquals("mocked-jwt-token", result);
    }

    @Test
    void hello_authenticatedUser() {
        // Given
        HttpServletRequest mockRequest = mock(HttpServletRequest.class);
        HttpServletResponse mockResponse = mock(HttpServletResponse.class);
        when(jwtAuthFilter.doFilter(mockRequest, mockResponse)).thenReturn(HttpServletResponse.SC_ACCEPTED);

        // When
        String result = userService.hello("Greetings", mockRequest, mockResponse);

        // Then
        assertEquals("Hello Pay", result);
    }

    @Test
    void hello_unauthenticatedUser() {
        // Given
        HttpServletRequest mockRequest = mock(HttpServletRequest.class);
        HttpServletResponse mockResponse = mock(HttpServletResponse.class);
        when(jwtAuthFilter.doFilter(mockRequest, mockResponse)).thenThrow(new RuntimeException("Error handling JWT"));

        // When/Then
        assertThrows(RuntimeException.class, () -> userService.hello("Greetings", mockRequest, mockResponse));
    }
}
