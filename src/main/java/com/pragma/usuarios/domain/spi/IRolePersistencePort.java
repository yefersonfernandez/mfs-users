package com.pragma.usuarios.domain.spi;

import com.pragma.usuarios.domain.model.RoleModel;

import java.util.Optional;

public interface IRolePersistencePort {

    void saveRole(RoleModel roleModel);

    Optional<RoleModel> getRoleById(Long roleId);

}
