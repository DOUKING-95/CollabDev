package com.team3.api_collab_dev.Exception;

public class ExistSameEmailException extends RuntimeException {

    public  ExistSameEmailException(String message){
        super(message);
    }
}
