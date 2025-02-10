package com.fortunae.controller;

import com.fortunae.dtos.request.DeleteUserRequest;
import com.fortunae.dtos.request.LoginRequest;
import com.fortunae.dtos.request.RegisterUserRequest;
import com.fortunae.dtos.request.UpdateDetailsRequest;
import com.fortunae.dtos.response.DeleteUserResponse;
import com.fortunae.dtos.response.LoginResponse;
import com.fortunae.dtos.response.RegisterUserResponse;
import com.fortunae.dtos.response.UpdateDetailsResponse;
import com.fortunae.execptions.InvalidDetailsException;
import com.fortunae.execptions.UserNotFoundException;
import com.fortunae.execptions.ViewerNotFoundException;
import com.fortunae.services.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/v1/User")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/registerUser")
    public ResponseEntity<?> registerUser(@Valid @RequestBody RegisterUserRequest request) {
        try{
            RegisterUserResponse response = userService.registerUser(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        }catch (ViewerNotFoundException | InvalidDetailsException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest request) {
        try {
            LoginResponse response = userService.login(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (InvalidDetailsException e) {
            return ResponseEntity.status(HttpStatus.valueOf(e.getMessage())).body(e.getMessage());
        }
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteUser(@Valid @RequestBody DeleteUserRequest request) {
        try {
            DeleteUserResponse response = userService.deleteUser(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (ViewerNotFoundException | InvalidDetailsException e){
            return ResponseEntity.status(HttpStatus.valueOf(e.getMessage())).body(e.getMessage());
    }
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateUser(@Valid @RequestBody UpdateDetailsRequest request) {
        try{
            UpdateDetailsResponse response = userService.updateUser(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch(UserNotFoundException e) {
            return ResponseEntity.status(HttpStatus.valueOf(e.getMessage())).body(e.getMessage());
        }
    }


}
