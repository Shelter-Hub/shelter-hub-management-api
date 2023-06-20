package com.shelterhub.exception;

import lombok.Data;

class PersistenceFailedException extends ErrorResponse {

    String code;

    public PersistenceFailedException(String message) {
        super(message);
    }
}
