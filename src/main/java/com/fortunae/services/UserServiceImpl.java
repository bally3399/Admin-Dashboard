package com.fortunae.services;

import com.fortunae.data.model.User;
import com.fortunae.data.repository.UserRepository;
import com.fortunae.dtos.request.*;
import com.fortunae.dtos.response.*;
import com.fortunae.execptions.InvalidDetailsException;
import com.fortunae.execptions.UserNotFoundException;
import com.fortunae.execptions.ViewerNotFoundException;
import com.fortunae.utils.JwtUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static com.fortunae.utils.ValidationUtils.isValidEmail;
import static com.fortunae.utils.ValidationUtils.isValidPassword;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public RegisterUserResponse registerUser(RegisterUserRequest request) {
        validateFields(request.getEmail(), request.getPassword());
        doesUserExists(request.getEmail());
        User user = modelMapper.map(request, User.class);
        user = userRepository.save(user);
        RegisterUserResponse response = modelMapper.map(user, RegisterUserResponse.class);
        response.setMessage("Viewer registered successfully");
        return response;
    }

    @Override
    public void deleteAll() {
        userRepository.deleteAll();
    }

    @Override
    public DeleteUserResponse deleteUser(DeleteUserRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new ViewerNotFoundException("User with email " + request.getEmail() + " not found"));

        if (user.getEmail().equals(request.getEmail())) {
            userRepository.delete(user);
            DeleteUserResponse response = new DeleteUserResponse();
            response.setMessage("Deleted viewer successfully");
            return response ;
        }

        throw new IllegalStateException("The user email does not match");
    }

    private void validateFields(String email, String password) {
        if (!isValidEmail(email)) throw new InvalidDetailsException("The email you entered is not correct");
        if (!isValidPassword(password))
            throw new InvalidDetailsException("Password must be between 8 and 16 characters long, including at least one uppercase letter, one lowercase letter, one number, and one special character (e.g., @, #, $, %, ^).");
    }

    private void doesUserExists(String email){
        User user = userRepository.findByEmail(email).orElse(null);
        if (user != null)throw new ViewerNotFoundException(String.format("User with email: %s already exits", email));
    }

    @Override
    public LoginResponse login(LoginRequest loginRequest) {
        String email = loginRequest.getEmail();
        String password = loginRequest.getPassword();
        return checkLoginDetail(email, password);
    }

    @Override
    public UpdateDetailsResponse updateUser(UpdateDetailsRequest updateUserRequest) {
        User user = userRepository.findByEmail(updateUserRequest.getEmail())
                .orElseThrow(() -> new UserNotFoundException("User with email " + updateUserRequest.getEmail() + " not found"));
        if(user.getEmail().equals(updateUserRequest.getEmail())){
            user.setEmail(updateUserRequest.getNewEmail());
            user.setFirstName(updateUserRequest.getFirstName());
            user.setLastName(updateUserRequest.getLastName());
            user.setPassword(updateUserRequest.getPassword());
            user.setUsername(updateUserRequest.getUsername());
            userRepository.save(user);
        }
        UpdateDetailsResponse response = new UpdateDetailsResponse();
        response.setMessage("Updated successfully");
        return response;
    }

    @Override
    public AssignRolesResponse assignRoles(AssignRolesRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        user.setRole(request.getRole());
        userRepository.save(user);
        AssignRolesResponse response = new AssignRolesResponse();
        response.setMessage("Role Assigned Successful");
        return response;
    }

    private LoginResponse checkLoginDetail(String email, String password) {
        Optional<User> optionalUser = userRepository.findByEmail(email);
        if (optionalUser.isPresent()){
            User user = optionalUser.get();
            if (user.getPassword().equals(password)) {
                return loginResponseMapper(user);
            } else {
                throw new InvalidDetailsException("Invalid username or password");
            }
        } else {
            throw new InvalidDetailsException("Invalid username or password");
        }
    }

    private LoginResponse loginResponseMapper(User admin) {
        LoginResponse loginResponse = new LoginResponse();
        String accessToken = JwtUtils.generateAccessToken(admin.getId());
        BeanUtils.copyProperties(admin, loginResponse);
        loginResponse.setJwtToken(accessToken);
        loginResponse.setMessage("Login Successful");
        loginResponse.setRole(admin.getRole());
        return loginResponse;
    }


}
