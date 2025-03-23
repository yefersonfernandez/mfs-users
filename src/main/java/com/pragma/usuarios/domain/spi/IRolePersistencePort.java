package com.pragma.usuarios.domain.spi;

import com.pragma.usuarios.domain.model.RoleModel;

public interface IRolePersistencePort {

    void saveRole(RoleModel roleModel);

    RoleModel getRoleById(Long roleId);

}
