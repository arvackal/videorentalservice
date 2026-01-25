package com.example.videorentalservice.service;

import com.example.videorentalservice.dto.AdminCreateUserRequest;
import com.example.videorentalservice.dto.RegisterUserRequest;
import com.example.videorentalservice.dto.UpdateUserRequest;
import com.example.videorentalservice.dto.UserDetailsResponse;
import com.example.videorentalservice.entity.Role;
import com.example.videorentalservice.entity.User;
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

    private Role parseRole(String roleAsString){
        if(roleAsString.trim().isEmpty())
            return Role.CUSTOMER;

        try{
            Role role = Role.valueOf(roleAsString.toUpperCase());
            return role;
        }catch (IllegalArgumentException e){
            throw new UnknownRoleException(roleAsString);
        }
    }

    private User userCreator(String firstName, String lastName,
                             String email, String password,
                             Role role, User currentUser
    ){

        if(existByEmail(email))
            throw new UserExistsException(email);

        if(role ==Role.ADMIN && currentUser == null)
            throw new AccessDeniedException("Admin creation requires admin permission.");

        return User.builder()
                .firstName(firstName)
                .lastName(lastName)
                .email(email)
                .password(passwordEncoder.encode(password))
                .role(role)
                .build();
    }

    @Override
    public UserDetailsResponse registerUser(RegisterUserRequest request) {
            User user = userCreator(request.getFirstName(),
                    request.getLastName(),
                    request.getEmail(),
                    request.getPassword(),
                    Role.CUSTOMER,
                    null);

            userRepository.save(user);
            return new UserDetailsResponse(user);
    }

    @Override
    public UserDetailsResponse createUserByAdmin(String currentUserEmail, AdminCreateUserRequest request) {
        User currentUser = getByEmail(currentUserEmail);

        if(currentUser.getRole() != Role.ADMIN)
            throw new AccessDeniedException("Only admins can create admins.");

        Role newUserRole = parseRole(request.getRole());
        User user = userCreator(request.getFirstName(),
                        request.getLastName(),
                        request.getEmail(),
                        request.getPassword(),
                        newUserRole,
                        currentUser);
        userRepository.save(user);

        return new UserDetailsResponse(user);

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
    public void deleteUserById(String email, String currentUserEmail) {
        User currentUser = getByEmail(currentUserEmail);

        if(!currentUser.getEmail().equals(email) && currentUser.getRole() != Role.ADMIN){
            throw new AccessDeniedException("Access denied.");
        }

        User user = getByEmail(email);

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
