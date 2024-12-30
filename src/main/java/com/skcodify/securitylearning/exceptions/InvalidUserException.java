package com.skcodify.securitylearning.exceptions;

public class InvalidUserException extends Throwable {

    private String message;
    public InvalidUserException(String message) {
        this.message = message;
    }

    public String getMessage(){
        return message;
    }
}
