package com.pragma.usuarios.infrastructure.out.feignclients.mapper.request;

import com.pragma.usuarios.domain.model.RestaurantEmployeeModel;
import com.pragma.usuarios.infrastructure.out.feignclients.dto.request.RestaurantEmployeeRequestDto;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface IRestaurantEmployeeDtoMapper {

    RestaurantEmployeeRequestDto toRestaurantEmployeeRequestDto(RestaurantEmployeeModel restaurantEmployeeModel);

}
