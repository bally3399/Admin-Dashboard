package com.fortunae.services;

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

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class EditorServiceImplTest {

    @Autowired
    private EditorService editorService;
    private RegisterUserRequest request;
    private LoginResponse loginResponse;

    @BeforeEach
    public void setUp() {
        editorService.deleteAll();

        request = new RegisterUserRequest();
        request.setFirstName("Janet");
        request.setLastName("Doe");
        request.setUsername("Janney");
        request.setPassword("Password@123");
        request.setEmail("janney@gmail.com");
        editorService.registerEditor(request);

        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setEmail("janney@gmail.com");
        loginRequest.setPassword("Password@123");
        loginResponse = editorService.login(loginRequest);
    }
    @Test
    public void testThatViewerCanRegister(){
        assertEquals("janney@gmail.com", request.getEmail());

    }

    @Test
    public void testThatEditorCannotRegisterTwice(){
        RegisterUserRequest registerUserRequest = new RegisterUserRequest();
        registerUserRequest.setFirstName("James");
        registerUserRequest.setLastName("Jane");
        registerUserRequest.setEmail("jane@doe.com");
        registerUserRequest.setPassword("Password@1234");
        registerUserRequest.setUsername("janny");
        editorService.registerEditor(registerUserRequest);
        assertThrows(ViewerNotFoundException.class,() -> editorService.registerEditor(registerUserRequest));
    }


    @Test
    public void testThatEditorCanLogin(){
        assertEquals("Login Successful", loginResponse.getMessage());
    }

    @Test
    public void deleteEditorTest(){
        DeleteUserRequest request = new DeleteUserRequest();
        request.setEmail("janney@gmail.com");
        DeleteUserResponse response = editorService.deleteEditor(request);
        assertEquals("Deleted editor successfully", response.getMessage());

    }
}