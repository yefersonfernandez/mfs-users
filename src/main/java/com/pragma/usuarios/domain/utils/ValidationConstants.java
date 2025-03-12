package com.pragma.usuarios.domain.utils;

public class ValidationConstants {
    public static final String EMAIL_REGEX = "^[A-Za-z0-9+_.-]+@(.+)$";
    public static final String PHONE_REGEX = "^\\+?\\d{1,13}$";
    public static final String DOCUMENT_REGEX = "^\\d+$";
    public static final int LEGAL_AGE = 18;

    private ValidationConstants() {
        throw new IllegalStateException("Utility class");
    }
}
