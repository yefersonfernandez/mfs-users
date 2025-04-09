package com.pragma.usuarios.domain.usecase;

import com.pragma.usuarios.domain.api.IRoleServicePort;
import com.pragma.usuarios.domain.exception.RoleNotFoundException;
import com.pragma.usuarios.domain.model.RoleModel;
import com.pragma.usuarios.domain.spi.IRolePersistencePort;
import com.pragma.usuarios.domain.utils.ErrorMessages;

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
        return rolePersistencePort.getRoleById(roleId)
                .orElseThrow(() -> new RoleNotFoundException(ErrorMessages.roleNotFound(roleId)));
    }
}
