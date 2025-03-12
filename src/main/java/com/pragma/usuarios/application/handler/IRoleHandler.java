package com.pragma.usuarios.application.handler;

import com.pragma.usuarios.application.dto.request.RoleRequestDto;
import com.pragma.usuarios.application.dto.response.RoleResponseDto;

import java.util.List;

public interface IRoleHandler {

    void saveRole(RoleRequestDto roleRequestDto);

    RoleResponseDto getRoleById(Long idUserRole);

    List<RoleResponseDto> getAllRoles();

    void deleteRoleById(Long idUserRole);
}
