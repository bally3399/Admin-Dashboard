package com.fortunae.services;

import com.fortunae.dtos.request.DeleteUserRequest;
import com.fortunae.dtos.request.LoginRequest;
import com.fortunae.dtos.request.RegisterUserRequest;
import com.fortunae.dtos.response.DeleteUserResponse;
import com.fortunae.dtos.response.LoginResponse;
import com.fortunae.dtos.response.RegisterUserResponse;

public interface ViewerService {
    RegisterUserResponse registerViewer(RegisterUserRequest request);

    void deleteAll();

    DeleteUserResponse deleteViewer(DeleteUserRequest request);

    LoginResponse login(LoginRequest loginRequest);
}
