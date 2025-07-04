package com.team3.api_collab_dev.Exception;

public class IncorrectPasswordException extends  RuntimeException{
    public IncorrectPasswordException(String message) {
        super(message);
    }
}
