package com.pragma.usuarios.infrastructure.utils;

public class ApiPaths {
    public static final String BASE_USERS = "/api/v1/users";
    public static final String BASE_ROLES = "/api/v1/roles";

    public static final String CREATE_OWNER = "/owner";
    public static final String CREATE_EMPLOYEE = "/employee";
    public static final String USER_BY_ID = "/{id}";


    public static final String CREATE_ROLE = "/";
    public static final String ROLE_BY_ID = "/{id}";

    private ApiPaths() {
        throw new UnsupportedOperationException("Utility class");
    }
}