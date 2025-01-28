package com.fortunae.services;

import com.fortunae.data.model.Role;
import com.fortunae.data.model.User;
import com.fortunae.data.repository.UserRepository;
import com.fortunae.dtos.request.DeleteUserRequest;
import com.fortunae.dtos.request.LoginRequest;
import com.fortunae.dtos.request.RegisterUserRequest;
import com.fortunae.dtos.response.DeleteUserResponse;
import com.fortunae.dtos.response.LoginResponse;
import com.fortunae.dtos.response.RegisterUserResponse;
import com.fortunae.execptions.EditorNotFoundException;
import com.fortunae.execptions.InvalidDetailsException;
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
public class EditorServiceImpl implements EditorService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public RegisterUserResponse registerEditor(RegisterUserRequest request) {
        validateFields(request.getEmail(), request.getPassword());
        doesUserExists(request.getEmail());
        User user = modelMapper.map(request, User.class);
        user.setRole(Role.EDITOR);
        user = userRepository.save(user);
        RegisterUserResponse response = modelMapper.map(user, RegisterUserResponse.class);
        response.setMessage("Editor registered successfully");
        return response;
    }

    @Override
    public DeleteUserResponse deleteEditor(DeleteUserRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new EditorNotFoundException("User with email " + request.getEmail() + " not found"));

        if (user.getRole() == Role.EDITOR && user.getEmail().equals(request.getEmail())) {
            userRepository.delete(user);
            DeleteUserResponse response = new DeleteUserResponse();
            response.setMessage("Deleted editor successfully");
            return response ;
        }

        throw new IllegalStateException("The user is not a EDITOR or email does not match");
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
        loginResponse.setRole(admin.getRole().toString());
        return loginResponse;
    }


    @Override
    public void deleteAll() {
        userRepository.deleteAll();
    }
}
