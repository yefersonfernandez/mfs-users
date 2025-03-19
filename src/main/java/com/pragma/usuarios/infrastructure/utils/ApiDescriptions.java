package com.pragma.usuarios.infrastructure.utils;

public class ApiDescriptions {
    public static final String USERS_TAG = "Operations related to users";
    public static final String CREATE_OWNER_SUMMARY = "Create a new owner user";
    public static final String CREATE_OWNER_PARAM = "User data for registration";
    public static final String CREATE_OWNER_201 = "Owner created";
    public static final String CREATE_OWNER_409 = "Owner already exists";

    public static final String GET_USER_BY_ID_SUMMARY = "Retrieve user information by ID";
    public static final String GET_USER_BY_ID_SUCCESS = "User retrieved successfully";
    public static final String GET_USER_BY_ID_NOT_FOUND = "User not found";
    public static final String GET_USER_BY_ID_PARAMETER = "ID of the user to retrieve";

    private ApiDescriptions() {
        throw new UnsupportedOperationException("Utility class");
    }
}