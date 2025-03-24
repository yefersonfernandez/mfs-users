package com.pragma.usuarios.application.mapper.response;

import com.pragma.usuarios.application.dto.response.RoleResponseDto;
import com.pragma.usuarios.domain.model.RoleModel;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;


@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface IRoleResponseMapper {

    RoleResponseDto toRoleResponse(RoleModel roleModel);

}
