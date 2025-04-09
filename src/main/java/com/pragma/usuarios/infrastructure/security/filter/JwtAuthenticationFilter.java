package com.pragma.usuarios.infrastructure.security.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pragma.usuarios.infrastructure.exception.AuthCredentialsParsingException;
import com.pragma.usuarios.infrastructure.security.model.AuthCredentials;
import com.pragma.usuarios.infrastructure.security.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import static com.pragma.usuarios.infrastructure.security.util.SecurityConstants.*;

@RequiredArgsConstructor
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException {
        try {
            AuthCredentials credentials = objectMapper.readValue(request.getInputStream(), AuthCredentials.class);

            return authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(credentials.getEmail(), credentials.getPassword())
            );
        } catch (IOException e) {
            throw new AuthCredentialsParsingException(AUTH_PROCESSING_ERROR, e);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response,
                                            FilterChain chain, Authentication authResult)
            throws IOException, ServletException {

        final User user = (User) authResult.getPrincipal();
        final Collection<? extends GrantedAuthority> roles = authResult.getAuthorities();

        final Map<String, Object> claims = new HashMap<>();
        claims.put(BODY_KEY_AUTHORITIES, roles.stream()
                .map(GrantedAuthority::getAuthority)
                .toList());

        final String token = jwtUtil.generateToken(user.getUsername(), claims);

        response.addHeader(HEADER_AUTHORIZATION, TOKEN_PREFIX + token);
        response.setContentType(CONTENT_TYPE);
        response.setStatus(HttpServletResponse.SC_OK);

        final Map<String, Object> body = new HashMap<>();
        body.put(BODY_KEY_TOKEN, token);
        body.put(BODY_KEY_USERNAME, user.getUsername());
        body.put(BODY_KEY_MESSAGE, String.format(SUCCESSFUL_AUTH_MESSAGE, user.getUsername()));

        response.getWriter().write(objectMapper.writeValueAsString(body));
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
                                              AuthenticationException failed)
            throws IOException, ServletException {

        final Map<String, Object> body = new HashMap<>();
        body.put(BODY_KEY_MESSAGE, UNSUCCESSFUL_AUTH_MESSAGE);
        body.put(BODY_KEY_ERROR, failed.getMessage());

        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType(CONTENT_TYPE);
        response.getWriter().write(objectMapper.writeValueAsString(body));
    }
}
