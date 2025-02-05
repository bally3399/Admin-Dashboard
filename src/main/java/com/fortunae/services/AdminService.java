package com.fortunae.services;

import com.fortunae.dtos.request.*;
import com.fortunae.dtos.response.*;

public interface AdminService {
    RegisterUserResponse registerAdmin(RegisterAdminRequest registerAdminRequest);

    void deleteAll();

    LoginResponse login(LoginRequest loginRequest);

    RegisterUserResponse addUser(RegisterUserRequest registerUserRequest);

    DeleteUserResponse deleteUser(DeleteUserRequest deleteUserRequest);

    UpdateDetailsResponse updateDetails(UpdateDetailsRequest updateUserRequest);

    AssignRolesResponse assignRoles(AssignRolesRequest request);

}
