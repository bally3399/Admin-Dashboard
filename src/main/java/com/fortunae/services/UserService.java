package com.fortunae.services;

import com.fortunae.dtos.request.*;
import com.fortunae.dtos.response.*;

public interface UserService {
    RegisterUserResponse registerUser(RegisterUserRequest request);

    void deleteAll();

    DeleteUserResponse deleteUser(DeleteUserRequest request);

    LoginResponse login(LoginRequest loginRequest);

    UpdateDetailsResponse updateUser(UpdateDetailsRequest updateUserRequest);

    AssignRolesResponse assignRoles(AssignRolesRequest request);
}
