package com.pragma.usuarios.infrastructure.out.token;

import com.pragma.usuarios.domain.spi.ITokenPort;
import com.pragma.usuarios.infrastructure.exception.MissingTokenException;
import com.pragma.usuarios.infrastructure.security.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Optional;

import static com.pragma.usuarios.infrastructure.security.util.SecurityConstants.HEADER_AUTHORIZATION;
import static com.pragma.usuarios.infrastructure.security.util.SecurityConstants.TOKEN_PREFIX;
import static com.pragma.usuarios.infrastructure.utils.ErrorMessages.MISSING_TOKEN;

@RequiredArgsConstructor
public class TokenAdapter implements ITokenPort {

    private final JwtUtil jwtUtil;

    @Override
    public Optional<String> findBearerToken() {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();

        if (attributes == null) return Optional.empty();

        String header = attributes.getRequest().getHeader(HEADER_AUTHORIZATION);

        if (header == null || !header.startsWith(TOKEN_PREFIX)) {
            return Optional.empty();
        }

        return Optional.of(header.substring(TOKEN_PREFIX.length()));
    }

    @Override
    public Optional<Long> getAuthenticatedUserId(String token) {
        return Optional.ofNullable(jwtUtil.extractUserId(token));
    }

    @Override
    public Optional<String> getAuthenticatedUserRole(String token) {
        return Optional.ofNullable(jwtUtil.extractUserRole(token));
    }

    @Override
    public Optional<String> getAuthenticatedUsername(String token) {
        return Optional.ofNullable(jwtUtil.extractUsername(token));
    }
}