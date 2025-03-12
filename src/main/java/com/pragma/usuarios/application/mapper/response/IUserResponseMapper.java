package com.pragma.usuarios.application.mapper.response;

import com.pragma.usuarios.application.dto.response.UserResponseDto;
import com.pragma.usuarios.domain.model.UserModel;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;


@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface IUserResponseMapper {

    IRoleResponseMapper INSTANCE = Mappers.getMapper(IRoleResponseMapper.class);

    UserResponseDto toUserResponse(UserModel userModel);

}
