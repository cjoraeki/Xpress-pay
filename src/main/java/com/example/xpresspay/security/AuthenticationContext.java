package com.example.xpresspay.security;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.stereotype.Component;

@ToString
@Component
@Setter
@Getter
public class AuthenticationContext {
    private String email;
    private boolean authenticate;
}
