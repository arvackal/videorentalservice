package com.example.videorentalservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class RentalNotPossibleException extends RuntimeException{
    public RentalNotPossibleException(String reason){
        super(reason);
    }
}
