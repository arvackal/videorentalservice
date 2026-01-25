package com.example.videorentalservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class RentalNotFoundException extends RuntimeException{
    public RentalNotFoundException(Long id){
        super("Rental not found with Id: " + id);
    }
}
