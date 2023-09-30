package com.shelterhub.exception;

public class NotUpdatableException extends RuntimeException {
    private String message;

    public NotUpdatableException(String message) {
        super(message);
    }
}
