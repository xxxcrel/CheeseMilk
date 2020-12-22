package com.cheese.exception;

public class UploadFailureException extends RuntimeException{

    public UploadFailureException(String s) {
        super(s);
    }

    public UploadFailureException(Throwable throwable) {
        super(throwable);
    }
}
