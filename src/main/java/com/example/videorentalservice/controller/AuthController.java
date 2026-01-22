package com.example.videorentalservice.controller;

import com.example.videorentalservice.dto.CreateUserRequest;
import com.example.videorentalservice.dto.UserDetailsResponse;
import com.example.videorentalservice.entity.User;
import com.example.videorentalservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<UserDetailsResponse> register(
            @RequestBody CreateUserRequest request){

        UserDetailsResponse response = userService.createUser(null, request);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(response);
    }

    @PostMapping("/users")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UserDetailsResponse> createUserByAdmin(
            @RequestBody CreateUserRequest request,
            Authentication authentication
    ){
        String currentUserEmail = authentication.getName();

        UserDetailsResponse response = userService.createUser(currentUserEmail, request);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(response);
    }

}
