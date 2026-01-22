package com.example.videorentalservice.dto;

import com.example.videorentalservice.entity.User;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

@Data
public class UserDetailsResponse {
    private String firstName;
    private String lastName;
    private String email;

    public UserDetailsResponse(User user) {
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.email = user.getEmail();
    }
}
