package com.fortunae.controller;

import com.fortunae.dtos.request.*;
import com.fortunae.dtos.response.*;
import com.fortunae.execptions.*;
import com.fortunae.services.AdminService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

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
        }catch(EmailAlreadyExistException | InvalidDetailsException e){
            return ResponseEntity.status(HttpStatus.valueOf(e.getMessage())).body(e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest request) {
        try {
            LoginResponse response = adminService.login(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch(InvalidDetailsException e){
            return ResponseEntity.status(HttpStatus.valueOf(e.getMessage())).body(e.getMessage());
        }
    }

    @PostMapping("/addUser")
    public ResponseEntity<?> addUser(@Valid @RequestBody RegisterUserRequest request) {
        try{
            RegisterUserResponse response = adminService.addUser(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        }catch (SubAdminLimitExceededException | UserAlreadyExistException e){
            return ResponseEntity.status(HttpStatus.valueOf(e.getMessage())).body(e.getMessage());
        }
    }

    @DeleteMapping("/delete-user")
    public ResponseEntity<?> deleteUser(@Valid @RequestBody DeleteUserRequest request) {
        try {
            DeleteUserResponse response = adminService.deleteUser(request);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }catch (ViewerNotFoundException | InvalidDetailsException e){
            return ResponseEntity.status(HttpStatus.valueOf(e.getMessage())).body(e.getMessage());
        }
    }

    @PostMapping("/assign")
    public ResponseEntity<?> assignRole(@Valid @RequestBody AssignRolesRequest request) {
        try {
            AssignRolesResponse response = adminService.assignRoles(request);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }catch (UserNotFoundException e){
            return ResponseEntity.status(HttpStatus.valueOf(e.getMessage())).body(e.getMessage());
        }
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateDetails(@Valid @RequestBody UpdateDetailsRequest request) {
        try {
            UpdateDetailsResponse response = adminService.updateDetails(request);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch(UserNotFoundException e) {
            return ResponseEntity.status(HttpStatus.valueOf(e.getMessage())).body(e.getMessage());
        }

    }


    @GetMapping("/getActive")
    public ResponseEntity<?> getActiveUser() {
        long response = adminService.getActiveUser();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
    @GetMapping("getTotal")
    public ResponseEntity<?> getTotalUser() {
        Long response = adminService.getTotalNumOfUser();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/newSignups")
    public ResponseEntity<Long> getNewSignups() {
        LocalDate oneWeekAgo = LocalDate.now().minusWeeks(1);
        Long newSignups = adminService.getNewSignups(oneWeekAgo);
        return ResponseEntity.status(HttpStatus.OK).body(newSignups);
    }





}
