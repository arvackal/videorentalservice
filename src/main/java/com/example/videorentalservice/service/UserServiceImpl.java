package com.example.videorentalservice.service;

import com.example.videorentalservice.dto.CreateUserRequest;
import com.example.videorentalservice.dto.UpdateUserRequest;
import com.example.videorentalservice.entity.Role;
import com.example.videorentalservice.entity.User;
import com.example.videorentalservice.exception.UnknownRoleException;
import com.example.videorentalservice.exception.UserNotFoundException;
import com.example.videorentalservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public User getById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));

        return user;
    }

    private Role parseRole(CreateUserRequest request){
        String roleAsString = request.getRole();

        if(roleAsString.trim().isEmpty())
            return Role.CUSTOMER;

        try{
            Role role = Role.valueOf(roleAsString.toUpperCase());
            return role;
        }catch (IllegalArgumentException e){
            throw new UnknownRoleException(roleAsString);
        }
    }

    @Override
    public User createUser(CreateUserRequest request, User currentUser) {
        Role role = parseRole(request);

        if(role == Role.ADMIN && currentUser.getRole() != Role.ADMIN)
            throw new AccessDeniedException("Only admins can create admins.");

        User user = User.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(role)
                .build();

        return user;
    }

    @Override
    public List<User> getAllUsers() {
        return List.of();
    }

    @Override
    public void deleteUser(Long id) {

    }

    @Override
    public User updateUser(Long id, UpdateUserRequest request) {
        return null;
    }
}
