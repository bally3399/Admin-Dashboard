package com.fortunae.services;

import com.fortunae.data.model.Role;
import com.fortunae.dtos.request.*;
import com.fortunae.dtos.response.*;
import com.fortunae.execptions.AdminExistException;
import com.fortunae.execptions.SubAdminLimitExceededException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.*;



@SpringBootTest
public class AdminServiceImplTest {
    @Autowired
    private AdminService adminService;
    @Autowired
    private UserService userService;

    private RegisterAdminRequest registerAdminRequest;
    private LoginResponse loginResponse;
    @BeforeEach
    public void setUp() {
        adminService.deleteAll();
        userService.deleteAll();
        registerAdminRequest = new RegisterAdminRequest();
        registerAdminRequest.setFirstName("John");
        registerAdminRequest.setLastName("Doe");
        registerAdminRequest.setEmail("john@doe.com");
        registerAdminRequest.setPassword("Password@123");
        registerAdminRequest.setUsername("johny");
        adminService.registerAdmin(registerAdminRequest);

        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setEmail("john@doe.com");
        loginRequest.setPassword("Password@123");
        loginResponse = adminService.login(loginRequest);


    }

    @Test
    public void testThatAdminCanRegister(){
        assertEquals("john@doe.com", registerAdminRequest.getEmail());

    }

    @Test
    public void testThatAdminCannotRegisterTwice(){
        RegisterAdminRequest registerAdminRequest1 = new RegisterAdminRequest();
        registerAdminRequest1.setFirstName("James");
        registerAdminRequest1.setLastName("Jane");
        registerAdminRequest1.setEmail("jane@doe.com");
        registerAdminRequest1.setPassword("Password@1234");
        registerAdminRequest1.setUsername("janny");

        adminService.registerAdmin(registerAdminRequest1);

        assertThrows(AdminExistException.class,() -> adminService.registerAdmin(registerAdminRequest1));
    }

    @Test
    public void testThatAdminCanLogin(){
        assertEquals("Login Successful", loginResponse.getMessage());
    }

    @Test
    public void testThatAdminCanUpdateDetails(){
        UpdateDetailsRequest request = new UpdateDetailsRequest();

    }

    @Test
    public void testThatAdminCanAddUser(){
        RegisterUserRequest registerUserRequest = new RegisterUserRequest();
        registerUserRequest.setFirstName("Alison");
        registerUserRequest.setLastName("Jacob");
        registerUserRequest.setEmail("alice@doe.com");
        registerUserRequest.setPassword("Password@1234");
        registerUserRequest.setUsername("Alice");
        registerUserRequest.setRole(Role.VIEWER);
        RegisterUserResponse response = adminService.addUser(registerUserRequest);
        assertEquals("alice@doe.com", response.getEmail());
    }

    @Test
    public void testThatAdminCanDeleteUser(){
        RegisterUserRequest registerUserRequest = new RegisterUserRequest();
        registerUserRequest.setFirstName("Jame");
        registerUserRequest.setLastName("Johnson");
        registerUserRequest.setEmail("janett@doe.com");
        registerUserRequest.setPassword("Password@1234");
        registerUserRequest.setUsername("janny");
        registerUserRequest.setRole(Role.VIEWER);
        RegisterUserResponse response = adminService.addUser(registerUserRequest);

        DeleteUserRequest deleteUserRequest = new DeleteUserRequest();
        deleteUserRequest.setEmail("janett@doe.com");
        DeleteUserResponse deleteUserResponse = adminService.deleteUser(deleteUserRequest);
        assertEquals("Deleted viewer successfully", deleteUserResponse.getMessage());
    }
    @Test
    public void testThatAdminCanUpdateUser(){
        RegisterUserRequest registerUserRequest = new RegisterUserRequest();
        registerUserRequest.setFirstName("Jetty");
        registerUserRequest.setLastName("Jane");
        registerUserRequest.setEmail("jetty@doe.com");
        registerUserRequest.setPassword("Password@1234");
        registerUserRequest.setUsername("janny");
        registerUserRequest.setRole(Role.VIEWER);
        adminService.addUser(registerUserRequest);

        UpdateDetailsRequest updateUserRequest = new UpdateDetailsRequest();
        updateUserRequest.setEmail("jetty@doe.com");
        updateUserRequest.setNewEmail("betty@doe.com");
        updateUserRequest.setFirstName("Betty");
        updateUserRequest.setPassword("@Password11");
        updateUserRequest.setLastName("Joy");
        updateUserRequest.setUsername("Jane");
        updateUserRequest.setRole(Role.EDITOR);
        UpdateDetailsResponse response = adminService.updateDetails(updateUserRequest);
        assertEquals("Updated successfully", response.getMessage());
    }

    @Test
    public void testThatAdminCanAssignRole() {
        RegisterUserRequest registerUserRequest = new RegisterUserRequest();
        registerUserRequest.setFirstName("Betty");
        registerUserRequest.setLastName("Joy");
        registerUserRequest.setEmail("betty1@doe.com");
        registerUserRequest.setPassword("@Password11");
        registerUserRequest.setUsername("Jane");
        registerUserRequest.setRole(Role.VIEWER);
        adminService.addUser(registerUserRequest);

        AssignRolesRequest request = new AssignRolesRequest();
        request.setEmail("betty1@doe.com");
        request.setRole(Role.EDITOR);
        AssignRolesResponse response = adminService.assignRoles(request);

        assertEquals("Role Assigned Successful", response.getMessage());
    }

    @Test
    public void testAdminCannotAddMoreThanFiveSubAdmins() {
        for (int i = 0; i < 5; i++) {
            RegisterUserRequest request = new RegisterUserRequest();
            request.setEmail("subadmin" + i + "@doe.com");
            request.setPassword("Password@1234");
            request.setUsername("subadmin" + i);
            request.setRole(Role.ADMIN);
            adminService.addUser(request);
        }

        RegisterUserRequest extraSubAdmin = new RegisterUserRequest();
        extraSubAdmin.setEmail("subadmin6@doe.com");
        extraSubAdmin.setPassword("Password@1234");
        extraSubAdmin.setUsername("subadmin6");
        extraSubAdmin.setRole(Role.ADMIN);

        assertThrows(SubAdminLimitExceededException.class, () -> {
            adminService.addUser(extraSubAdmin);
        });
    }

}