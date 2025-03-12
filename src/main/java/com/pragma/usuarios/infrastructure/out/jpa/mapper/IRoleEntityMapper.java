package com.pragma.usuarios.infrastructure.out.jpa.mapper;

import com.pragma.usuarios.domain.model.RoleModel;
import com.pragma.usuarios.infrastructure.out.jpa.entity.RoleEntity;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface IRoleEntityMapper {

    RoleEntity toRoleEntity(RoleModel roleModel);

    RoleModel toRoleModel(RoleEntity roleEntity);

    List<RoleModel> toRoleModelList(List<RoleEntity> roleEntityList);
}
