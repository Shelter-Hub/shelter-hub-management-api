package com.shelterhub.exception;

public class InvalidValueException extends RuntimeException {
    private String message;
    public InvalidValueException(String message) {
        super(message);
    }
}