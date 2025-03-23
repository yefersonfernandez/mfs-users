package com.pragma.usuarios.application.handler.impl;

import com.pragma.usuarios.application.dto.request.RoleRequestDto;
import com.pragma.usuarios.application.dto.response.RoleResponseDto;
import com.pragma.usuarios.application.handler.IRoleHandler;
import com.pragma.usuarios.application.mapper.request.IRoleRequestMapper;
import com.pragma.usuarios.application.mapper.response.IRoleResponseMapper;
import com.pragma.usuarios.domain.api.IRoleServicePort;
import com.pragma.usuarios.domain.model.RoleModel;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class RoleHandlerImpl implements IRoleHandler {

    private final IRoleResponseMapper roleResponseMapper;
    private final IRoleRequestMapper roleRequestMapper;
    private final IRoleServicePort roleServicePort;

    @Override
    public void saveRole(RoleRequestDto roleRequestDto) {
        RoleModel role = roleRequestMapper.toRoleResponse(roleRequestDto);
        roleServicePort.saveRole(role);
    }

    @Override
    public RoleResponseDto getRoleById(Long idUserRole) {
        return roleResponseMapper.toRoleResponse(roleServicePort.getRoleById(idUserRole));
    }

}
