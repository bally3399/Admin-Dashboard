package com.fortunae.services;

import com.fortunae.dtos.request.RegisterUserRequest;
import com.fortunae.dtos.response.RegisterUserResponse;

public interface AdminService {
    RegisterUserResponse registerAdmin(RegisterUserRequest registerUserRequest);

    void deleteAll();
}
