package com.decagon.scorecardapi.exception;

public class AccountNotActiveException extends RuntimeException{
    public AccountNotActiveException(String message){
        super(message);
    }
}
