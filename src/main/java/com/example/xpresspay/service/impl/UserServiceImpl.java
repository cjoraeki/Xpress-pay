package com.example.xpresspay.service.impl;

import com.example.xpresspay.dto.request.LoginDto;
import com.example.xpresspay.dto.request.UserRequestDto;
import com.example.xpresspay.dto.response.UserResponseDto;
import com.example.xpresspay.entity.User;
import com.example.xpresspay.repository.UserRepository;
import com.example.xpresspay.service.UserService;
import com.example.xpresspay.utils.JWTAuthFilter;
import com.example.xpresspay.utils.JWTUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final JWTUtils jwtUtils;
    private final JWTAuthFilter jwtAuthFilter;

    @Override
    public UserResponseDto signUp(UserRequestDto userRequestDto) {
        User user = new User();

        ModelMapper modelMapper = new ModelMapper();
        modelMapper.map(userRequestDto, user);

        userRepository.save(user);

        return modelMapper.map(user, UserResponseDto.class);
    }

    @Override
    public String authenticateUser(LoginDto loginDto) throws Exception {
        Optional<User> userOptional = userRepository.findByEmail(loginDto.getEmail());

        if (userOptional.isPresent()) {
            log.info("User found -> {}", userOptional.get().getEmail());
            User user = userOptional.get();
            String jwt = jwtUtils.generateToken(user);
            log.info("Exiting method authenticateUser");
            log.info(jwt);
            return jwt;
        } else {
            throw new Exception("User not found");
        }
    }

    @Override
    public String hello(String greet, HttpServletRequest request, HttpServletResponse response) {
        if (isUserAuthenticated(request, response)) {
            greet = "Hello Pay";
            return greet;
        } else {
            throw new RuntimeException("Error handling JWT");
        }
    }

    private boolean isUserAuthenticated(HttpServletRequest request, HttpServletResponse response) {
        try {
            return jwtAuthFilter.doFilter(request, response) == HttpServletResponse.SC_ACCEPTED;
        } catch (RuntimeException e) {
            return false;
        }
    }

}
