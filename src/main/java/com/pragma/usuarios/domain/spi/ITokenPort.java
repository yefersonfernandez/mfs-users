package com.pragma.usuarios.domain.spi;

import java.util.Optional;

public interface ITokenPort {

    Optional<String> findBearerToken();

    Optional<String> getAuthenticatedUserRole(String token);

    Optional<String> getAuthenticatedUsername(String token);

    Optional<Long> getAuthenticatedUserId(String token);
}
