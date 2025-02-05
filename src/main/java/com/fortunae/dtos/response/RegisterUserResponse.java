package com.fortunae.dtos.response;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class RegisterUserResponse {
    private String firstName;
    private String lastName;
    private String email;
    private String username;
    private String message;
}
