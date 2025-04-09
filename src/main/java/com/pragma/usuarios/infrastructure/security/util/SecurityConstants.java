package com.pragma.usuarios.infrastructure.security.util;


public final class SecurityConstants {

    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_AUTHORIZATION = "Authorization";
    public static final String CONTENT_TYPE = "application/json";

    public static final String[] SWAGGER_PATHS = {
            "/swagger-ui/**",
            "/v3/api-docs/**",
            "/swagger-resources/**"
    };

    public static final String SUCCESSFUL_AUTH_MESSAGE = "Hola %s, has iniciado sesión con éxito!";
    public static final String UNSUCCESSFUL_AUTH_MESSAGE = "Error en la autenticación: usuario o contraseña incorrectos!";
    public static final String INVALID_JWT_MESSAGE = "El token JWT no es válido!";
    public static final String USER_NOT_FOUND_MESSAGE = "El usuario %s no existe en el sistema!";
    public static final String AUTH_PROCESSING_ERROR = "Error al procesar las credenciales";

    public static final String CLAIM_AUTHORITIES = "authorities";

    public static final String BODY_KEY_TOKEN = "token";
    public static final String BODY_KEY_USERNAME = "username";
    public static final String BODY_KEY_MESSAGE = "message";
    public static final String BODY_KEY_ERROR = "error";
    public static final String BODY_KEY_AUTHORITIES = "authorities";

    private SecurityConstants() {
        throw new IllegalStateException("Utility class");
    }
}
