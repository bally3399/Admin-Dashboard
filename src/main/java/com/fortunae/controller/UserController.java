package com.fortunae.controller;

import com.fortunae.dtos.request.DeleteUserRequest;
import com.fortunae.dtos.request.LoginRequest;
import com.fortunae.dtos.request.RegisterUserRequest;
import com.fortunae.dtos.request.UpdateDetailsRequest;
import com.fortunae.dtos.response.DeleteUserResponse;
import com.fortunae.dtos.response.LoginResponse;
import com.fortunae.dtos.response.RegisterUserResponse;
import com.fortunae.dtos.response.UpdateDetailsResponse;
import com.fortunae.execptions.UserAlreadyExistException;
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
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/registerUser")
    public ResponseEntity<?> registerUser(@Valid @RequestBody RegisterUserRequest request) {
        try{
            RegisterUserResponse response = userService.registerUser(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        }catch (ViewerNotFoundException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest request) {
        LoginResponse response = userService.login(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteUser(@Valid @RequestBody DeleteUserRequest request) {
        DeleteUserResponse response = userService.deleteUser(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateUser(@Valid @RequestBody UpdateDetailsRequest request) {
        UpdateDetailsResponse response = userService.updateUser(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/getActive")
    public ResponseEntity<?> getActiveUser() {
        long response = userService.getActiveUser();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
    @GetMapping("getTotal")
    public ResponseEntity<?> getTotalUser() {
        Long response = userService.getTotalNumOfUser();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/newSignups")
    public ResponseEntity<Long> getNewSignups() {
        LocalDate oneWeekAgo = LocalDate.now().minusWeeks(1);
        Long newSignups = userService.getNewSignups(oneWeekAgo);
        return ResponseEntity.status(HttpStatus.OK).body(newSignups);
    }
}
