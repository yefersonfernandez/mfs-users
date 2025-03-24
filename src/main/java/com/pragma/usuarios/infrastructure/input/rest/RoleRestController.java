package com.pragma.usuarios.infrastructure.input.rest;

import com.pragma.usuarios.application.dto.request.RoleRequestDto;
import com.pragma.usuarios.application.dto.response.RoleResponseDto;
import com.pragma.usuarios.application.handler.IRoleHandler;
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
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(ApiPaths.BASE_ROLES)
@RequiredArgsConstructor
@Tag(name = "roles", description = ApiDescriptions.ROLES_TAG)
public class RoleRestController {

    private final IRoleHandler roleHandler;

    @Operation(summary = ApiDescriptions.CREATE_ROLE_SUMMARY,
            description = ApiDescriptions.CREATE_ROLE_DESCRIPTION)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = ApiDescriptions.CREATE_ROLE_SUCCESS),
            @ApiResponse(responseCode = "409", description = ApiDescriptions.CREATE_ROLE_CONFLICT)
    })
    @PostMapping(ApiPaths.CREATE_ROLE)
    public ResponseEntity<Void> createRole(
            @Parameter(description = ApiDescriptions.CREATE_ROLE_PARAM, required = true)
            @RequestBody RoleRequestDto roleRequestDto
    ) {
        roleHandler.saveRole(roleRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Operation(summary = ApiDescriptions.GET_ROLE_BY_ID_SUMMARY,
            description = ApiDescriptions.GET_ROLE_BY_ID_DESCRIPTION)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = ApiDescriptions.GET_ROLE_BY_ID_SUCCESS),
            @ApiResponse(responseCode = "404", description = ApiDescriptions.GET_ROLE_BY_ID_NOT_FOUND)
    })
    @GetMapping(ApiPaths.ROLE_BY_ID)
    public ResponseEntity<RoleResponseDto> getRoleById(
            @Parameter(description = ApiDescriptions.GET_ROLE_BY_ID_PARAM, required = true)
            @PathVariable(name = "id") Long roleId
    ) {
        RoleResponseDto response = roleHandler.getRoleById(roleId);
        return ResponseEntity.ok(response);
    }
}
