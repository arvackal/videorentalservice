package com.example.videorentalservice.controller;

import com.example.videorentalservice.dto.UpdateUserRequest;
import com.example.videorentalservice.dto.UserDetailsResponse;
import com.example.videorentalservice.entity.User;
import com.example.videorentalservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/me")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<UserDetailsResponse> getMyProfile(Authentication  authentication){
        String email = authentication.getName();
        System.out.println("Reqeust caught email: " + email);
        User user = userService.getByEmail(email);
        UserDetailsResponse response = new UserDetailsResponse(user);

        return ResponseEntity.ok(response);
    }

    @PutMapping("/me")
    public ResponseEntity<UserDetailsResponse> updateMyProfile(
            @RequestBody UpdateUserRequest request,
            Authentication authentication
            ){

        String currentUserEmail = authentication.getName();

        UserDetailsResponse response = userService.updateUser(request.getEmail(), currentUserEmail, request);

        return ResponseEntity.ok(response);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public ResponseEntity<List<UserDetailsResponse>> getAllUsers(Authentication authentication){
        String currentUserEmail = authentication.getName();

        List<UserDetailsResponse> usersDetails = userService.getAllUsers(currentUserEmail);

        return ResponseEntity.ok(usersDetails);
    }
}
