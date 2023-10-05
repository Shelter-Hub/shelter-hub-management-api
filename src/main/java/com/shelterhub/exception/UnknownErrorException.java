package com.shelterhub.exception;

public class UnknownErrorException extends RuntimeException {
    private String message;

    public UnknownErrorException(String message) {
        super(message);
    }
}
