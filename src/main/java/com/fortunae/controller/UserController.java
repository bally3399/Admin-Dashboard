package com.fortunae.controller;

import com.fortunae.dtos.request.DeleteUserRequest;
import com.fortunae.dtos.request.LoginRequest;
import com.fortunae.dtos.request.RegisterUserRequest;
import com.fortunae.dtos.response.DeleteUserResponse;
import com.fortunae.dtos.response.LoginResponse;
import com.fortunae.dtos.response.RegisterUserResponse;
import com.fortunae.services.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/User")
@RequiredArgsConstructor
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/registerUser")
    public ResponseEntity<?> registerUser(@Valid @RequestBody RegisterUserRequest request) {
        RegisterUserResponse response = userService.registerUser(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest request) {
        LoginResponse response = userService.login(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/delete")
    public ResponseEntity<?> deleteUser(@Valid @RequestBody DeleteUserRequest request) {
        DeleteUserResponse response = userService.deleteUser(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }


}
