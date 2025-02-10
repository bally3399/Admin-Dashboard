package com.fortunae.services;

import com.fortunae.data.model.Admin;
import com.fortunae.data.model.Role;
import com.fortunae.data.model.User;
import com.fortunae.data.repository.AdminRepository;
import com.fortunae.data.repository.UserRepository;
import com.fortunae.dtos.request.*;
import com.fortunae.dtos.response.*;
import com.fortunae.execptions.AdminExistException;
import com.fortunae.execptions.InvalidDetailsException;
import com.fortunae.utils.JwtUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;

import static com.fortunae.utils.ValidationUtils.isValidEmail;
import static com.fortunae.utils.ValidationUtils.isValidPassword;

@Service
public class AdminServiceImpl implements AdminService{
    @Autowired
    private UserService userService;

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public RegisterUserResponse registerAdmin(RegisterAdminRequest registerUserRequest) {
        validateFields(registerUserRequest.getEmail(), registerUserRequest.getPassword());
        doesUserExists(registerUserRequest.getEmail());
        Admin admin = modelMapper.map(registerUserRequest, Admin.class);
        admin = adminRepository.save(admin);
        RegisterUserResponse response = modelMapper.map(admin, RegisterUserResponse.class);
        response.setMessage("Admin registered successfully");
        return response;
    }

    @Override
    public void deleteAll() {
        adminRepository.deleteAll();
    }

    private void validateFields(String email, String password) {
        if (!isValidEmail(email)) throw new InvalidDetailsException("The email you entered is not correct");
        if (!isValidPassword(password))
            throw new InvalidDetailsException("Password must be between 8 and 16 characters long, including at least one uppercase letter, one lowercase letter, one number, and one special character (e.g., @, #, $, %, ^).");
    }

    private void doesUserExists(String email){
        Admin admin = adminRepository.findByEmail(email);
        if (admin != null) throw new AdminExistException(String.format("Admin with email: %s already exits", email));
    }

    @Override
    public LoginResponse login(LoginRequest loginRequest) {
        String email = loginRequest.getEmail();
        String password = loginRequest.getPassword();
        return checkLoginDetail(email, password);
    }

    @Override
    public RegisterUserResponse addUser(RegisterUserRequest registerUserRequest) {
        return userService.registerUser(registerUserRequest);
    }

    @Override
    public DeleteUserResponse deleteUser(DeleteUserRequest deleteUserRequest) {
        return userService.deleteUser(deleteUserRequest);
    }

    @Override
    public UpdateDetailsResponse updateDetails(UpdateDetailsRequest updateUserRequest) {
        return userService.updateUser(updateUserRequest);
    }

    @Override
    public AssignRolesResponse assignRoles(AssignRolesRequest request) {
        return userService.assignRoles(request);
    }

    @Override
    public Long getTotalNumOfUser() {
        return userService.getTotalNumOfUser();
    }

    @Override
    public long getActiveUser() {
        return userService.getActiveUser();
    }

    @Override
    public Long getNewSignups(LocalDate fromDate) {
        return userService.getNewSignups(fromDate);
    }

    private LoginResponse checkLoginDetail(String email, String password) {
        Admin optionalUser = adminRepository.findByEmail(email);
        if (optionalUser != null){
            if (optionalUser.getPassword().equals(password)) {
                return loginResponseMapper(optionalUser);
            } else {
                throw new InvalidDetailsException("Invalid username or password");
            }
        } else {
            throw new InvalidDetailsException("Invalid username or password");
        }
    }

    private LoginResponse loginResponseMapper(Admin admin) {
        LoginResponse loginResponse = new LoginResponse();
        String accessToken = JwtUtils.generateAccessToken(admin.getId());
        BeanUtils.copyProperties(admin, loginResponse);
        loginResponse.setJwtToken(accessToken);
        loginResponse.setMessage("Login Successful");
        loginResponse.setRole(admin.getRoles());
        return loginResponse;
    }



}
