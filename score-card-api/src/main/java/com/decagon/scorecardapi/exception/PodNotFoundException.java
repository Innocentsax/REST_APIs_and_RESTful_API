package com.decagon.scorecardapi.exception;

public class PodNotFoundException extends RuntimeException{
   public PodNotFoundException(String message){
            super(message);
   }
}
