package com.decagon.scorecardapi.dto.responsedto;

import java.time.LocalDateTime;


public class APIResponse<T> {
    private final boolean success;
    private final String message;
    private T data;

    public APIResponse(boolean success, String message, T data) {
        this.success = success;
        this.message = message;
        this.data = data;
    }

    public APIResponse(boolean success, String message) {
        this.success = success;
        this.message = message;
    }


    public T getData() {
        return data;
    }

    public boolean isSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }

    public String getTimeStamp(){
        return LocalDateTime.now().toString();
    }
}