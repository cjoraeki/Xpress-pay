package com.example.xpresspay.dto.request;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UserRequestDto {
    private String firstname;
    private String lastname;
    private String email;
    private String password;
}
