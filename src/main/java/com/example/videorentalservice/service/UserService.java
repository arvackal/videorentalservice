package com.example.videorentalservice.service;

import com.example.videorentalservice.dto.CreateUserRequest;
import com.example.videorentalservice.dto.UpdateUserRequest;
import com.example.videorentalservice.dto.UserDetailsResponse;
import com.example.videorentalservice.entity.User;
import org.springframework.stereotype.Service;

import java.util.List;

public interface UserService {
    public User getById(Long id);
    public User getByEmail(String email);
    public UserDetailsResponse createUser(String currentUserEmail, CreateUserRequest request);
    public List<UserDetailsResponse> getAllUsers(String currentEmail);
    public void deleteUserById(Long id, Long currentUserId);
    public UserDetailsResponse updateUser(String email, String currentUserEmail, UpdateUserRequest request);
}
