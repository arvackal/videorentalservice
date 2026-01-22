package com.example.videorentalservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class UserExistsException extends RuntimeException{
    public UserExistsException(String email){super("Account with email: " + email + " already exist.");}
}
