package com.shelterhub.exception;


class PersistenceFailedException extends ErrorResponse {

    private String message;

    public PersistenceFailedException(String message) {
        super(message);
    }
}
