package com.decagon.scorecardapi.exception;

public class StackNotFoundException extends RuntimeException{
    public StackNotFoundException(String message) {
        super(message);
    }
}
