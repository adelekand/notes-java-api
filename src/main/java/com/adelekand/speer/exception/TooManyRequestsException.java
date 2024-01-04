package com.adelekand.speer.exception;

public class TooManyRequestsException extends Exception {
    public TooManyRequestsException(String errorMessage) {
        super(errorMessage);
    }
}

