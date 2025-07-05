package com.pragma.usuarios.domain.utils;

public class ValidationConstants {
    public static final String EMAIL_REGEX = "^[A-Za-z0-9+_.-]+@(.+)$";
    public static final String PHONE_REGEX = "^\\+?\\d{1,13}$";
    public static final String DOCUMENT_REGEX = "^\\d+$";
    public static final int LEGAL_AGE = 18;

    public static final String ROLE_ADMIN = "ROLE_ADMIN";
    public static final String ROLE_OWNER = "ROLE_OWNER";

    public static final long ROLE_ID_ADMIN = 1L;
    public static final long ROLE_ID_OWNER = 2L;
    public static final long ROLE_ID_EMPLOYEE = 3L;
    public static final long ROLE_ID_CLIENT = 4L;

    private ValidationConstants() {
        throw new IllegalStateException("Utility class");
    }
}
