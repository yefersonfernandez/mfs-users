package com.pragma.usuarios.domain.spi;

import com.pragma.usuarios.domain.model.RoleModel;

import java.util.List;

public interface IRolePersistencePort {

    RoleModel saveRole(RoleModel roleModel);

    List<RoleModel> getAllRoles();

    RoleModel getRoleById(Long roleId);

    void deleteRoleById(Long roleId);
}
