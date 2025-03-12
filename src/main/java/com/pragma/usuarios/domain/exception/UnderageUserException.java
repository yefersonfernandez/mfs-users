package com.pragma.usuarios.domain.exception;

public class UnderageUserException extends RuntimeException {

    public UnderageUserException(String message) {
        super(message);
    }

}