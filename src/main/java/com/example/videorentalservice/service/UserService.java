package com.example.videorentalservice.service;

import com.example.videorentalservice.dto.CreateUserRequest;
import com.example.videorentalservice.dto.UpdateUserRequest;
import com.example.videorentalservice.entity.User;
import org.springframework.stereotype.Service;

import java.util.List;

public interface UserService {
    public User getById(Long id);
    public User createUser(CreateUserRequest request, User currentUser);
    public List<User> getAllUsers();
    public void deleteUser(Long id);
    public User updateUser(Long id, UpdateUserRequest request);
}
