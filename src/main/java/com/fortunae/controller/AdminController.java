package com.fortunae.controller;

import com.fortunae.dtos.request.DeleteUserRequest;
import com.fortunae.dtos.request.LoginRequest;
import com.fortunae.dtos.request.RegisterUserRequest;
import com.fortunae.dtos.response.DeleteUserResponse;
import com.fortunae.dtos.response.LoginResponse;
import com.fortunae.dtos.response.RegisterUserResponse;
import com.fortunae.services.AdminService;
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
@RequestMapping("/api/v1/Admin")
@RequiredArgsConstructor
public class AdminController {
    @Autowired
    private AdminService adminService;

    @PostMapping("/registerAdmin")
    public ResponseEntity<?> registerAdmin(@Valid @RequestBody RegisterUserRequest request) {
        RegisterUserResponse response = adminService.registerAdmin(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest request) {
        LoginResponse response = adminService.login(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }


}
