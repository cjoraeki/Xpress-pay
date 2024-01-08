package com.example.xpresspay.dto.response;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UserResponseDto {
    private String firstname;
    private String lastname;
    private String email;
    private String password;
}
