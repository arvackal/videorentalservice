package com.example.videorentalservice.service;

import com.example.videorentalservice.dto.CreateUserRequest;
import com.example.videorentalservice.dto.UpdateUserRequest;
import com.example.videorentalservice.dto.UserDetailsResponse;
import com.example.videorentalservice.entity.Role;
import com.example.videorentalservice.entity.User;
import com.example.videorentalservice.exception.AdminNotFoundException;
import com.example.videorentalservice.exception.UnknownRoleException;
import com.example.videorentalservice.exception.UserExistsException;
import com.example.videorentalservice.exception.UserNotFoundException;
import com.example.videorentalservice.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

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


    private Boolean existByEmail(String email){
        return userRepository.existsByEmail(email);
    }

    @Override
    public User getByEmail(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException(email));

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

    private User.UserBuilder userCreator(CreateUserRequest request){

        if(existByEmail(request.getEmail()))
            throw new UserExistsException(request.getEmail());

        return User.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()));
    }

    @Override
    public UserDetailsResponse createUser(String currentUserEmail, CreateUserRequest request) {
        Role role = parseRole(request);

        if(role == Role.CUSTOMER){
            User user = userCreator(request).role(Role.CUSTOMER).build();

            return new UserDetailsResponse(user);
        }
        else{
            User currentUser = getByEmail(currentUserEmail);

            if(currentUser.getRole() != Role.ADMIN)
                throw new AccessDeniedException("Only admins can create admins.");

            User user = User.builder()
                    .firstName(request.getFirstName())
                    .lastName(request.getLastName())
                    .email(request.getEmail())
                    .password(passwordEncoder.encode(request.getPassword()))
                    .role(Role.ADMIN)
                    .build();

            return new UserDetailsResponse(user);
        }
    }


    @Override
    public List<UserDetailsResponse> getAllUsers(String currentUserEmail) {

        User user = getByEmail(currentUserEmail);

        if(user.getRole() != Role.ADMIN)
            throw new AccessDeniedException("Access denied.");
        List<User> users = userRepository.findAll();

        return users.stream().map(UserDetailsResponse::new).toList();
    }

    @Override
    public void deleteUserById(Long id, Long currentUserId) {
        User user = getById(id);
        User currentUser = getById(currentUserId);

        if(!Objects.equals(id, currentUserId) && currentUser.getRole() != Role.ADMIN){
            throw new AccessDeniedException("Access denied.");
        }

        userRepository.delete(user);
    }

    @Transactional
    @Override
    public UserDetailsResponse updateUser(String email, String currentUserEmail, UpdateUserRequest request) {
        User user = getByEmail(email);

        if(!currentUserEmail.equals(user.getEmail()))
            throw new AccessDeniedException("Access Denied.");

        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setEmail(request.getEmail());

        return new UserDetailsResponse(user);
    }
}
