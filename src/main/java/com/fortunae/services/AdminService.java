package com.fortunae.services;

import com.fortunae.dtos.request.LoginRequest;
import com.fortunae.dtos.request.RegisterUserRequest;
import com.fortunae.dtos.response.LoginResponse;
import com.fortunae.dtos.response.RegisterUserResponse;

public interface AdminService {
    RegisterUserResponse registerAdmin(RegisterUserRequest registerUserRequest);

    void deleteAll();

    LoginResponse login(LoginRequest loginRequest);
}
