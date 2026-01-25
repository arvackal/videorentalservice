package com.example.videorentalservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class RentalAlreadyReturnedException extends RuntimeException{
    public RentalAlreadyReturnedException(Long id){
        super("Rental with id: " + id + " already returned");
    }
}
