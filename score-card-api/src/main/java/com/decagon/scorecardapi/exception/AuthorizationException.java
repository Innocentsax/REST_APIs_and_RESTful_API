package com.decagon.scorecardapi.exception;

public class AuthorizationException extends RuntimeException{
    public AuthorizationException(String message){
        super(message);
    }
}
