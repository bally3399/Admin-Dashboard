package com.fortunae.services;

import com.fortunae.dtos.request.LoginRequest;
import com.fortunae.dtos.request.RegisterUserRequest;
import com.fortunae.dtos.response.LoginResponse;
import com.fortunae.dtos.response.RegisterUserResponse;
import com.fortunae.execptions.AdminExistException;
import com.fortunae.execptions.UserAlreadyExistException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.*;



@SpringBootTest
public class AdminServiceImplTest {
    @Autowired
    private AdminService adminService;

    private RegisterUserRequest registerUserRequest;
    private LoginResponse loginResponse;
    @BeforeEach
    public void setUp() {
        adminService.deleteAll();

        registerUserRequest = new RegisterUserRequest();
        registerUserRequest.setFirstName("John");
        registerUserRequest.setLastName("Doe");
        registerUserRequest.setEmail("john@doe.com");
        registerUserRequest.setPassword("Password@123");
        registerUserRequest.setUsername("johny");
        adminService.registerAdmin(registerUserRequest);

        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setEmail("john@doe.com");
        loginRequest.setPassword("Password@123");
        loginResponse = adminService.login(loginRequest);


    }

    @Test
    public void testThatAdminCanRegister(){
        assertEquals("john@doe.com", registerUserRequest.getEmail());

    }

    @Test
    public void testThatAdminCannotRegisterTwice(){
        RegisterUserRequest registerUserRequest = new RegisterUserRequest();
        registerUserRequest.setFirstName("James");
        registerUserRequest.setLastName("Jane");
        registerUserRequest.setEmail("jane@doe.com");
        registerUserRequest.setPassword("Password@1234");
        registerUserRequest.setUsername("janny");

        adminService.registerAdmin(registerUserRequest);

        assertThrows(AdminExistException.class,() -> adminService.registerAdmin(registerUserRequest));
    }

    @Test
    public void testThatAdminCanLogin(){
        assertEquals("Login Successful", loginResponse.getMessage());
    }
}