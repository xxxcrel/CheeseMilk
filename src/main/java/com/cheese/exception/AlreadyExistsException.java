package com.cheese.exception;


public class AlreadyExistsException extends RuntimeException{


    public AlreadyExistsException(String msg) {
        super(msg);
    }

}
