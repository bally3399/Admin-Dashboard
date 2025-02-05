package com.fortunae.dtos.response;


import com.fortunae.data.model.Role;
import lombok.*;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class LoginResponse {
    private String message;
    private String jwtToken;
    private Role role;
    private String firstName;
    private String lastName;


}
