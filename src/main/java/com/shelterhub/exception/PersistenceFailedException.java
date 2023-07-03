package com.shelterhub.exception;


public class PersistenceFailedException extends RuntimeException {

    private String message;

    public PersistenceFailedException(String message) {
        super(message);
    }
}
