package com.decagon.scorecardapi.exception;

public class SquadAlreadyExistException extends RuntimeException{
    public SquadAlreadyExistException(String message) {
        super(message);
    }
}
