package com.pragma.usuarios.domain.utils;

public class ErrorMessages {

    public static final String NAME_REQUIRED = "Name is required";
    public static final String EMAIL_REQUIRED = "Email is required";
    public static final String PHONE_REQUIRED = "Phone number is required";
    public static final String DOCUMENT_REQUIRED = "Document is required";
    public static final String BIRTHDATE_REQUIRED = "Birthdate is required";
    public static final String ROLE_REQUIRED = "Role is required";

    public static final String INVALID_EMAIL = "Invalid email format";
    public static final String INVALID_PHONE = "Invalid phone number format";
    public static final String INVALID_DOCUMENT = "Document must contain only numbers";

    public static final String UNDERAGE_USER = "The user must be of legal age";

    private static final String ERROR_NOT_FOUND_TEMPLATE = "The %s with ID %d was not found in the system.";
    private static final String ERROR_NOT_FOUND_BY_FIELD_TEMPLATE = "The %s with %s '%s' was not found in the system.";

    public static final String ENTITY_USER = "User";
    public static final String ENTITY_ROLE = "Role";
    public static final String ENTITY_RESTAURANT = "Restaurant";

    public static final String FIELD_EMAIL = "email";

    public static final String TOKEN_REQUIRED = "Authentication token is required.";
    public static final String AUTHENTICATED_USER_ID_NOT_FOUND = "Authenticated user ID could not be retrieved from the token.";

    private ErrorMessages() {
        throw new UnsupportedOperationException("Utility class");
    }

    public static String userNotFound(Long id) {
        return String.format(ERROR_NOT_FOUND_TEMPLATE, ENTITY_USER, id);
    }

    public static String roleNotFound(Long id) {
        return String.format(ERROR_NOT_FOUND_TEMPLATE, ENTITY_ROLE, id);
    }

    public static String restaurantNotFound(Long ownerId) {
        return String.format(ERROR_NOT_FOUND_TEMPLATE, ENTITY_RESTAURANT, ownerId);
    }

    public static String userNotFoundByEmail(String email) {
        return String.format(ERROR_NOT_FOUND_BY_FIELD_TEMPLATE, ENTITY_USER, FIELD_EMAIL, email);
    }
}