package com.pragma.usuarios.infrastructure.security.model;

import lombok.Data;

@Data
public class AuthCredentials {
    private String email;
    private String password;
}
