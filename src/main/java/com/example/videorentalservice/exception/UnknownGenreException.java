package com.example.videorentalservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class UnknownGenreException extends RuntimeException{
    public UnknownGenreException(String genre){
        super("Unknown Genre: " + genre);
    }
}
