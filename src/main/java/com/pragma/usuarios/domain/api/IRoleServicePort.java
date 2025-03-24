package com.pragma.usuarios.domain.api;

import com.pragma.usuarios.domain.model.RoleModel;

public interface IRoleServicePort {

    void saveRole(RoleModel roleModel);

    RoleModel getRoleById(Long roleId);

}
