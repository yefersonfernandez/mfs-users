package com.pragma.usuarios.infrastructure.out.jpa.adapter;

import com.pragma.usuarios.domain.model.RoleModel;
import com.pragma.usuarios.domain.spi.IRolePersistencePort;
import com.pragma.usuarios.infrastructure.out.jpa.entity.RoleEntity;
import com.pragma.usuarios.infrastructure.out.jpa.mapper.IRoleEntityMapper;
import com.pragma.usuarios.infrastructure.out.jpa.repository.IRoleRepository;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@RequiredArgsConstructor
public class RoleJpaAdapter implements IRolePersistencePort {

    private final IRoleRepository roleRepository;

    private final IRoleEntityMapper roleEntityMapper;

    @Override
    public void saveRole(RoleModel roleModel) {
        roleRepository.save(roleEntityMapper.toRoleEntity(roleModel));
    }

    @Override
    public Optional<RoleModel> getRoleById(Long roleId) {
        return roleRepository.findById(roleId).map(roleEntityMapper::toRoleModel);
    }

}
