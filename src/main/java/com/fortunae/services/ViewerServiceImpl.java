package com.fortunae.services;

import com.fortunae.data.model.Role;
import com.fortunae.data.model.User;
import com.fortunae.data.repository.UserRepository;
import com.fortunae.dtos.request.DeleteUserRequest;
import com.fortunae.dtos.request.RegisterUserRequest;
import com.fortunae.dtos.response.DeleteUserResponse;
import com.fortunae.dtos.response.RegisterUserResponse;
import com.fortunae.execptions.AdminExistException;
import com.fortunae.execptions.InvalidDetailsException;
import com.fortunae.execptions.ViewerNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.fortunae.utils.ValidationUtils.isValidEmail;
import static com.fortunae.utils.ValidationUtils.isValidPassword;

@Service
public class ViewerServiceImpl implements ViewerService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public RegisterUserResponse registerViewer(RegisterUserRequest request) {
        validateFields(request.getEmail(), request.getPassword());
        doesUserExists(request.getEmail());
        User user = modelMapper.map(request, User.class);
        user.setRole(Role.VIEWER);
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
    public DeleteUserResponse deleteViewer(DeleteUserRequest request) {
        doesUserExists(request.getEmail());
        return null;
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

}
