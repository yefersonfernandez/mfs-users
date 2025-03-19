package com.pragma.usuarios.infrastructure.utils;

public class ApiPaths {
    public static final String BASE_USERS = "/api/v1/users";
    public static final String CREATE_OWNER = "/owners";
    public static final String USER_BY_ID = "/{id}";

    private ApiPaths() {
        throw new UnsupportedOperationException("Utility class");
    }
}
