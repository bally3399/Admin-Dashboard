package com.fortunae.controller;

import com.fortunae.dtos.request.*;
import com.fortunae.dtos.response.*;
import com.fortunae.execptions.EmailAlreadyExistException;
import com.fortunae.execptions.SubAdminLimitExceededException;
import com.fortunae.execptions.UserAlreadyExistException;
import com.fortunae.services.AdminService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/Admin")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class AdminController {
    @Autowired
    private AdminService adminService;

    @PostMapping("/registerAdmin")
    public ResponseEntity<?> registerAdmin(@Valid @RequestBody RegisterAdminRequest request) {
        try{
            RegisterUserResponse response = adminService.registerAdmin(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        }catch(EmailAlreadyExistException e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest request) {
        LoginResponse response = adminService.login(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/addUser")
    public ResponseEntity<?> addUser(@Valid @RequestBody RegisterUserRequest request) {
        try{
            RegisterUserResponse response = adminService.addUser(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        }catch (SubAdminLimitExceededException | UserAlreadyExistException e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @DeleteMapping("/delete-user")
    public ResponseEntity<?> deleteUser(@Valid @RequestBody DeleteUserRequest request) {
        DeleteUserResponse response = adminService.deleteUser(request);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PostMapping("/assign")
    public ResponseEntity<?> assignRole(@Valid @RequestBody AssignRolesRequest request) {
        AssignRolesResponse response = adminService.assignRoles(request);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateDetails(@Valid @RequestBody UpdateDetailsRequest request) {
        UpdateDetailsResponse response = adminService.updateDetails(request);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }







}
