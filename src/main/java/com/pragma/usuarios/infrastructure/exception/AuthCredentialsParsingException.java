package com.pragma.usuarios.infrastructure.exception;

public class AuthCredentialsParsingException extends RuntimeException {
    public AuthCredentialsParsingException(String message, Throwable cause) {
        super(message, cause);
    }
}

