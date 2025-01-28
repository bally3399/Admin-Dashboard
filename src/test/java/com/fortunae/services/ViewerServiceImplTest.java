package com.fortunae.services;

import com.fortunae.dtos.request.DeleteUserRequest;
import com.fortunae.dtos.request.RegisterUserRequest;
import com.fortunae.dtos.response.DeleteUserResponse;
import com.fortunae.dtos.response.RegisterUserResponse;
import com.fortunae.execptions.AdminExistException;
import com.fortunae.execptions.ViewerNotFoundException;
import com.mongodb.internal.bulk.DeleteRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
public class ViewerServiceImplTest {
    @Autowired
    private ViewerService viewerService;
    private RegisterUserRequest request;
    @BeforeEach
    public void setUp() {
        viewerService.deleteAll();

        request = new RegisterUserRequest();
        request.setFirstName("Janet");
        request.setLastName("Doe");
        request.setUsername("Janney");
        request.setPassword("Password@123");
        request.setEmail("janney@gmail.com");
        viewerService.registerViewer(request);
    }
    @Test
    public void testThatViewerCanRegister(){

        assertEquals("janney@gmail.com", request.getEmail());

    }

    @Test
    public void testThatAdminCannotRegisterTwice(){
        RegisterUserRequest registerUserRequest = new RegisterUserRequest();
        registerUserRequest.setFirstName("James");
        registerUserRequest.setLastName("Jane");
        registerUserRequest.setEmail("jane@doe.com");
        registerUserRequest.setPassword("Password@1234");
        registerUserRequest.setUsername("janny");
        viewerService.registerViewer(registerUserRequest);
        assertThrows(ViewerNotFoundException.class,() -> viewerService.registerViewer(registerUserRequest));
    }



    @Test
    public void deleteViewerTest(){
        DeleteUserRequest request = new DeleteUserRequest();
        request.setEmail("janney@gmail.com");
        DeleteUserResponse response = viewerService.deleteViewer(request);
//        assertEquals()

    }

}