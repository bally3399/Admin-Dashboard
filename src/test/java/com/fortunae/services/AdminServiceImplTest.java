package com.fortunae.services;

import com.fortunae.data.model.Role;
import com.fortunae.dtos.request.*;
import com.fortunae.dtos.response.*;
import com.fortunae.execptions.AdminExistException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.*;



@SpringBootTest
public class AdminServiceImplTest {
    @Autowired
    private AdminService adminService;

    private RegisterAdminRequest registerAdminRequest;
    private LoginResponse loginResponse;
    @BeforeEach
    public void setUp() {
        adminService.deleteAll();

        registerAdminRequest = new RegisterAdminRequest();
        registerAdminRequest.setFirstName("John");
        registerAdminRequest.setLastName("Doe");
        registerAdminRequest.setEmail("john@doe.com");
        registerAdminRequest.setPassword("Password@123");
        registerAdminRequest.setUsername("johny");
        adminService.registerAdmin(registerAdminRequest);

        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setEmail("john@doe.com");
        loginRequest.setPassword("Password@123");
        loginResponse = adminService.login(loginRequest);


    }

    @Test
    public void testThatAdminCanRegister(){
        assertEquals("john@doe.com", registerAdminRequest.getEmail());

    }

    @Test
    public void testThatAdminCannotRegisterTwice(){
        RegisterAdminRequest registerAdminRequest1 = new RegisterAdminRequest();
        registerAdminRequest1.setFirstName("James");
        registerAdminRequest1.setLastName("Jane");
        registerAdminRequest1.setEmail("jane@doe.com");
        registerAdminRequest1.setPassword("Password@1234");
        registerAdminRequest1.setUsername("janny");

        adminService.registerAdmin(registerAdminRequest1);

        assertThrows(AdminExistException.class,() -> adminService.registerAdmin(registerAdminRequest1));
    }

    @Test
    public void testThatAdminCanLogin(){
        assertEquals("Login Successful", loginResponse.getMessage());
    }

    @Test
    public void testThatAdminCanUpdateDetails(){
        UpdateDetailsRequest request = new UpdateDetailsRequest();

    }

    @Test
    public void testThatAdminCanAddUser(){
        RegisterUserRequest registerUserRequest = new RegisterUserRequest();
        registerUserRequest.setFirstName("Jame");
        registerUserRequest.setLastName("Jane");
        registerUserRequest.setEmail("janet@doe.com");
        registerUserRequest.setPassword("Password@1234");
        registerUserRequest.setUsername("janny");
        registerUserRequest.setRole(Role.VIEWER);
        RegisterUserResponse response = adminService.addUser(registerUserRequest);
        assertEquals("janet@doe.com", response.getEmail());
    }

    @Test
    public void testThatAdminCanDeleteUser(){
        RegisterUserRequest registerUserRequest = new RegisterUserRequest();
        registerUserRequest.setFirstName("Jame");
        registerUserRequest.setLastName("Johnson");
        registerUserRequest.setEmail("janett@doe.com");
        registerUserRequest.setPassword("Password@1234");
        registerUserRequest.setUsername("janny");
        registerUserRequest.setRole(Role.VIEWER);
        RegisterUserResponse response = adminService.addUser(registerUserRequest);

        DeleteUserRequest deleteUserRequest = new DeleteUserRequest();
        deleteUserRequest.setEmail("janett@doe.com");
        DeleteUserResponse deleteUserResponse = adminService.deleteUser(deleteUserRequest);
        assertEquals("Deleted viewer successfully", deleteUserResponse.getMessage());
    }
    @Test
    public void testThatAdminCanUpdateUser(){
        RegisterUserRequest registerUserRequest = new RegisterUserRequest();
        registerUserRequest.setFirstName("Jame");
        registerUserRequest.setLastName("Jane");
        registerUserRequest.setEmail("johnson@doe.com");
        registerUserRequest.setPassword("Password@1234");
        registerUserRequest.setUsername("janny");
        registerUserRequest.setRole(Role.VIEWER);
        adminService.addUser(registerUserRequest);

        UpdateDetailsRequest updateUserRequest = new UpdateDetailsRequest();
        updateUserRequest.setFirstName("Johnson");
        updateUserRequest.setPassword("@Password11");
        updateUserRequest.setLastName("Joy");
        updateUserRequest.setUsername("Jane");
        updateUserRequest.setRole(Role.EDITOR);
        updateUserRequest.setEmail("jane@doe.com");
        UpdateDetailsResponse response = adminService.updateDetails(updateUserRequest);
        assertEquals("Updated Successfully", response.getMessage());
    }

    @Test
    public void testThatAdminCanAssignRole(){
        AssignRolesRequest request = new AssignRolesRequest();
        request.setEmail("Janett@doe.com");
        request.setRole(Role.VIEWER);
        AssignRolesResponse response = adminService.assignRoles(request);
        assertEquals("Role Assigned Successful", response.getMessage());

    }


}