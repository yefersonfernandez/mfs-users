package com.pragma.usuarios.domain.utils;

public class ErrorMessages {

    public static final String NAME_REQUIRED = "Name is required";
    public static final String EMAIL_REQUIRED = "Email is required";
    public static final String PHONE_REQUIRED = "Phone number is required";
    public static final String DOCUMENT_REQUIRED = "Document is required";
    public static final String BIRTHDATE_REQUIRED = "Birthdate is required";

    public static final String INVALID_EMAIL = "Invalid email format";
    public static final String INVALID_PHONE = "Invalid phone number format";
    public static final String INVALID_DOCUMENT = "Document must contain only numbers";
    public static final String INVALID_ROLE = "The specified role does not exist.";

    public static final String UNDERAGE_USER = "The user must be of legal age";

    private ErrorMessages() {
        throw new UnsupportedOperationException("Utility class");
    }
}