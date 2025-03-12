package com.pragma.usuarios.domain.api;

import com.pragma.usuarios.domain.model.RoleModel;

import java.util.List;

public interface IRoleServicePort {

    void saveRole(RoleModel roleModel);

    RoleModel getRoleById(Long roleId);

    List<RoleModel> getAllRoles();

    void deleteRoleById(Long roleId);
}
