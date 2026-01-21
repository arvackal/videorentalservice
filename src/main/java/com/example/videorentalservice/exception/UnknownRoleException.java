package com.example.videorentalservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class UnknownRoleException extends RuntimeException{
    public UnknownRoleException(String role){
        super("Unknown role: " + role);
    }
}
