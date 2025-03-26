package com.pragma.usuarios.infrastructure.input.rest;

import com.pragma.usuarios.application.dto.request.UserRequestDto;
import com.pragma.usuarios.application.dto.response.UserResponseDto;
import com.pragma.usuarios.application.handler.IUserHandler;
import com.pragma.usuarios.infrastructure.utils.ApiPaths;
import com.pragma.usuarios.infrastructure.utils.ApiDescriptions;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(ApiPaths.BASE_USERS)
@RequiredArgsConstructor
@Tag(name = "users", description = ApiDescriptions.USERS_TAG)
public class UserRestController {

    private final IUserHandler userHandler;

    @Operation(summary = ApiDescriptions.CREATE_OWNER_SUMMARY,
            description = ApiDescriptions.CREATE_OWNER_DESCRIPTION)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = ApiDescriptions.CREATE_OWNER_201),
            @ApiResponse(responseCode = "409", description = ApiDescriptions.CREATE_OWNER_409)
    })
    @PostMapping(ApiPaths.CREATE_OWNER)
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<Void> createOwner(
            @Parameter(description = ApiDescriptions.CREATE_OWNER_PARAM, required = true)
            @RequestBody UserRequestDto userRequestDto
    ) {
        userHandler.saveUser(userRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Operation(summary = ApiDescriptions.GET_USER_BY_ID_SUMMARY,
            description = ApiDescriptions.GET_USER_BY_ID_DESCRIPTION)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = ApiDescriptions.GET_USER_BY_ID_SUCCESS),
            @ApiResponse(responseCode = "404", description = ApiDescriptions.GET_USER_BY_ID_NOT_FOUND)
    })
    @GetMapping(ApiPaths.USER_BY_ID)
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<UserResponseDto> getUserById(
            @Parameter(description = ApiDescriptions.GET_USER_BY_ID_PARAMETER, required = true)
            @PathVariable(name = "id") Long userId
    ) {
        UserResponseDto response = userHandler.getUserById(userId);
        return ResponseEntity.ok(response);
    }
}