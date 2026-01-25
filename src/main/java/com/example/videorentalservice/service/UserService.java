package com.example.videorentalservice.service;

import com.example.videorentalservice.dto.AdminCreateUserRequest;
import com.example.videorentalservice.dto.RegisterUserRequest;
import com.example.videorentalservice.dto.UpdateUserRequest;
import com.example.videorentalservice.dto.UserDetailsResponse;
import com.example.videorentalservice.entity.User;

import java.util.List;

public interface UserService {
    public User getById(Long id);
    public User getByEmail(String email);
    public UserDetailsResponse registerUser(RegisterUserRequest request);
    public UserDetailsResponse createUserByAdmin(String currentUserEmail, AdminCreateUserRequest request);
    public List<UserDetailsResponse> getAllUsers(String currentEmail);
    public void deleteUserById(String email, String currentUserEmail);
    public UserDetailsResponse updateUser(String email, String currentUserEmail, UpdateUserRequest request);
}
