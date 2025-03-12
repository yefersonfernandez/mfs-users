package com.pragma.usuarios.application.mapper.request;

import com.pragma.usuarios.application.dto.request.RoleRequestDto;
import com.pragma.usuarios.domain.model.RoleModel;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface IRoleRequestMapper {

    RoleModel toRoleResponse(RoleRequestDto roleRequestDto);

}
