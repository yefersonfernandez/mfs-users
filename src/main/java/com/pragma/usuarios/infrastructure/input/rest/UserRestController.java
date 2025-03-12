package com.pragma.usuarios.infrastructure.input.rest;

import com.pragma.usuarios.application.dto.request.UserRequestDto;
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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(ApiPaths.BASE_USERS)
@RequiredArgsConstructor
@Tag(name = "users", description = ApiDescriptions.USERS_TAG)
public class UserRestController {

    private final IUserHandler userHandler;

    @Operation(summary = ApiDescriptions.CREATE_OWNER_SUMMARY)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = ApiDescriptions.CREATE_OWNER_201),
            @ApiResponse(responseCode = "409", description = ApiDescriptions.CREATE_OWNER_409)
    })
    @PostMapping(ApiPaths.CREATE_OWNER)
    public ResponseEntity<Void> createOwner(
            @Parameter(description = ApiDescriptions.CREATE_OWNER_PARAM, required = true)
            @RequestBody UserRequestDto userRequest
    ) {
        userHandler.saveUser(userRequest);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
