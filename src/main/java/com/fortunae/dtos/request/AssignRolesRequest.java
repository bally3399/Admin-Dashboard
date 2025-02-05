package com.fortunae.dtos.request;

import com.fortunae.data.model.Role;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class AssignRolesRequest {
    private String email;
    private Role role;

}
