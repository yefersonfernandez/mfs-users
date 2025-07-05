package com.pragma.usuarios.infrastructure.out.feignclients.mapper.response;

import com.pragma.usuarios.domain.model.RestaurantModel;
import com.pragma.usuarios.infrastructure.out.feignclients.dto.response.RestaurantResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface IRestaurantDtoMapper {

    RestaurantModel toRestaurantModel(RestaurantResponseDto restaurantResponseDto);

}