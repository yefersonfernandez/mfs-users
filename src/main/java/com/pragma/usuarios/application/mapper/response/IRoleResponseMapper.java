package com.pragma.usuarios.application.mapper.response;

import com.pragma.usuarios.application.dto.response.RoleResponseDto;
import com.pragma.usuarios.domain.model.RoleModel;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface IRoleResponseMapper {

    RoleResponseDto toRoleResponse(RoleModel roleModel);

    List<RoleResponseDto> toResponseList(List<RoleModel> roleList);
}
