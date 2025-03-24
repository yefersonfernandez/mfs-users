package com.pragma.usuarios.infrastructure.utils;

public class ApiDescriptions {

    public static final String USERS_TAG = "User Operations";

    public static final String CREATE_OWNER_SUMMARY = "Create a new owner";
    public static final String CREATE_OWNER_DESCRIPTION = "Register a new owner with the provided details";
    public static final String CREATE_OWNER_PARAM = "Owner data for registration";
    public static final String CREATE_OWNER_201 = "Owner successfully created";
    public static final String CREATE_OWNER_409 = "Owner already exists";

    public static final String GET_USER_BY_ID_SUMMARY = "Get user details by ID";
    public static final String GET_USER_BY_ID_DESCRIPTION = "Retrieve information about a specific user using their ID";
    public static final String GET_USER_BY_ID_PARAMETER = "ID of the user to retrieve";
    public static final String GET_USER_BY_ID_SUCCESS = "User retrieved successfully";
    public static final String GET_USER_BY_ID_NOT_FOUND = "User not found";

    public static final String ROLES_TAG = "Role Operations";

    public static final String CREATE_ROLE_SUMMARY = "Create a new role";
    public static final String CREATE_ROLE_DESCRIPTION = "Register a new role with the provided details";
    public static final String CREATE_ROLE_PARAM = "Role data for registration";
    public static final String CREATE_ROLE_SUCCESS = "Role successfully created";
    public static final String CREATE_ROLE_CONFLICT = "Role already exists";

    public static final String GET_ROLE_BY_ID_SUMMARY = "Get role details by ID";
    public static final String GET_ROLE_BY_ID_DESCRIPTION = "Retrieve information about a specific role using its ID";
    public static final String GET_ROLE_BY_ID_PARAM = "ID of the role to retrieve";
    public static final String GET_ROLE_BY_ID_SUCCESS = "Role retrieved successfully";
    public static final String GET_ROLE_BY_ID_NOT_FOUND = "Role not found";

    private ApiDescriptions() {
        throw new UnsupportedOperationException("Utility class");
    }
}