package com.pragma.usuarios.domain.usecase;

import com.pragma.usuarios.application.dto.response.RoleResponseDto;
import com.pragma.usuarios.domain.api.IRoleServicePort;
import com.pragma.usuarios.domain.model.RoleModel;
import com.pragma.usuarios.domain.spi.IRolePersistencePort;

import java.util.List;

public class RoleUseCase implements IRoleServicePort {

    private final IRolePersistencePort rolePersistencePort;

    public RoleUseCase(IRolePersistencePort rolePersistencePort) {
        this.rolePersistencePort = rolePersistencePort;
    }

    @Override
    public void saveRole(RoleModel roleModel) {
        rolePersistencePort.saveRole(roleModel);
    }

    @Override
    public RoleModel getRoleById(Long roleId) {
        return rolePersistencePort.getRoleById(roleId);
    }

    @Override
    public List<RoleModel> getAllRoles() {
        return rolePersistencePort.getAllRoles();
    }

    @Override
    public void deleteRoleById(Long roleId) {
        rolePersistencePort.deleteRoleById(roleId);
    }
}
