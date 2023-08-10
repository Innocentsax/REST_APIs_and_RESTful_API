package com.example.blogpost.exception;

import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

public class InvalidEmailOrPasswordException extends RuntimeException {
    private  String message;
    private final LocalDateTime time = LocalDateTime.now();
    private HttpStatus status;

    public InvalidEmailOrPasswordException(String message) {
        this.message = message;
    }

    public InvalidEmailOrPasswordException(String message, HttpStatus status) {
        this.message = message;
        this.status = status;
    }
}
