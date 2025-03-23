package com.pragma.usuarios.infrastructure.out.jpa.mapper;

import com.pragma.usuarios.domain.model.UserModel;
import com.pragma.usuarios.infrastructure.out.jpa.entity.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;


@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface IUserEntityMapper {

    @Mapping(source = "roleModel", target = "roleEntity")
    UserEntity toUserEntity(UserModel userModel);

    @Mapping(source = "roleEntity", target = "roleModel")
    UserModel toUserModel(UserEntity userEntity);

}
