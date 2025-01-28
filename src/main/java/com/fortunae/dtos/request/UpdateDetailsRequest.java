package com.fortunae.dtos.request;

import com.fortunae.data.model.Role;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UpdateDetailsRequest {
    private String id;
    private String firstName;
    private String lastName;
    private String password;
    private String username;
    private String email;
    private Role role;

}
