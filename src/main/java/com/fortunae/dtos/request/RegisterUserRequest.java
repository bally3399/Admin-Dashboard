package com.fortunae.dtos.request;

import com.fortunae.data.model.Role;
import lombok.*;
import jakarta.validation.constraints.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class RegisterUserRequest {
    @NotBlank(message = "Username is required")
    @NotEmpty(message = "Username must not be empty")
    @Size(min = 3, max = 50, message = "Username must be between 3 and 50 characters")
    private String username;

    @NotBlank(message = "First name is required")
    @NotEmpty(message = "First name must not be empty")
    @Size(min = 2, max = 50, message = "First name must be between 2 and 50 characters")
    private String firstName;

    @NotBlank(message = "Last name is required")
    @NotEmpty(message = "Last name must not be empty")
    @Size(min = 2, max = 50, message = "Last name must be between 2 and 50 characters")
    private String lastName;

    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    private String email;

    @NotBlank(message = "Password is required")
    @NotEmpty(message = "Password must not be empty")
    private String password;

    @NotNull(message = "Role is required")
    private Role role;
}
