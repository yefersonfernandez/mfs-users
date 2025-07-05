package com.pragma.usuarios.infrastructure.out.feignclients.mapper.response;

import com.pragma.usuarios.domain.model.RestaurantEmployeeModel;
import com.pragma.usuarios.infrastructure.out.feignclients.dto.response.RestaurantEmployeeResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface IRestaurantEmployeeDtoMapper {

    RestaurantEmployeeModel toRestaurantEmployeeModel(RestaurantEmployeeResponseDto employeeResponseDto);

}
