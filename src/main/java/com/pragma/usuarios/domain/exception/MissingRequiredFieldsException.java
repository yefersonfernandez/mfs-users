package com.pragma.usuarios.domain.exception;

public class MissingRequiredFieldsException extends RuntimeException {

    public MissingRequiredFieldsException(String message) {
        super(message);
    }

}