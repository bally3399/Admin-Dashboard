package com.fortunae.services;

import com.fortunae.dtos.request.DeleteUserRequest;
import com.fortunae.dtos.request.LoginRequest;
import com.fortunae.dtos.request.RegisterUserRequest;
import com.fortunae.dtos.response.DeleteUserResponse;
import com.fortunae.dtos.response.LoginResponse;
import com.fortunae.dtos.response.RegisterUserResponse;

public interface EditorService {
    RegisterUserResponse registerEditor(RegisterUserRequest request);

    DeleteUserResponse deleteEditor(DeleteUserRequest request);

    LoginResponse login(LoginRequest loginRequest);

    void deleteAll();
}
