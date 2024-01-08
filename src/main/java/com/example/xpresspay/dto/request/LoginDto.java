package com.example.xpresspay.dto.request;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class LoginDto {
    private String email;
    private String password;
}
