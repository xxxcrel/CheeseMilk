package com.cheese.exception;

public class InvalidParameterException  extends RuntimeException{

    public InvalidParameterException(String message){
        super(message);
    }
}