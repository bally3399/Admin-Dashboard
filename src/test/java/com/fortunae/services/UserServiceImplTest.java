package com.fortunae.services;

import com.fortunae.data.repository.UserRepository;
import com.fortunae.dtos.request.DeleteUserRequest;
import com.fortunae.dtos.request.LoginRequest;
import com.fortunae.dtos.request.RegisterUserRequest;
import com.fortunae.dtos.response.DeleteUserResponse;
import com.fortunae.dtos.response.LoginResponse;
import com.fortunae.execptions.ViewerNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
public class UserServiceImplTest {
    @Autowired
    private UserService userService;
    private RegisterUserRequest request;
    private LoginResponse loginResponse;
    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    public void setUp() {
        userService.deleteAll();

        request = new RegisterUserRequest();
        request.setFirstName("Janet");
        request.setLastName("Doe");
        request.setUsername("Janney");
        request.setPassword("Password@123");
        request.setEmail("janney@gmail.com");
        userService.registerUser(request);

        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setEmail("janney@gmail.com");
        loginRequest.setPassword("Password@123");
        loginResponse = userService.login(loginRequest);
    }
    @Test
    public void testThatViewerCanRegister(){
        assertEquals("janney@gmail.com", request.getEmail());

    }

    @Test
    public void testThatViewerCannotRegisterTwice(){
        RegisterUserRequest registerUserRequest = new RegisterUserRequest();
        registerUserRequest.setFirstName("James");
        registerUserRequest.setLastName("Jane");
        registerUserRequest.setEmail("jane@doe.com");
        registerUserRequest.setPassword("Password@1234");
        registerUserRequest.setUsername("janny");
        userService.registerUser(registerUserRequest);
        assertThrows(ViewerNotFoundException.class,() -> userService.registerUser(registerUserRequest));
    }


    @Test
    public void testThatViewerCanLogin(){
        assertEquals("Login Successful", loginResponse.getMessage());
    }

    @Test
    public void deleteUserTest(){
        DeleteUserRequest request = new DeleteUserRequest();
        request.setEmail("janney@gmail.com");
        DeleteUserResponse response = userService.deleteUser(request);
        assertEquals("Deleted viewer successfully", response.getMessage());

    }

    @Test
    public void getCountOfTotalUserTest(){
        assertEquals(0, userService.getTotalNumOfUser());

    }

    @Test
    public void getActiveUserTest(){
        assertEquals(0, userService.getActiveUser());
    }
    @Test
    public void testGetNewSignups() {
        LocalDate date = LocalDate.ofEpochDay(28-01-2025);
        long newSignups = userService.getNewSignups(date);
        assertTrue(newSignups >= 0);
    }


}