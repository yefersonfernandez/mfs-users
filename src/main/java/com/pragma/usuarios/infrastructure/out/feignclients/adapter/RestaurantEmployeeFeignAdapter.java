package com.pragma.usuarios.infrastructure.out.feignclients.adapter;

import com.pragma.usuarios.domain.model.RestaurantEmployeeModel;
import com.pragma.usuarios.domain.spi.IRestaurantEmployeeFeignClientPort;
import com.pragma.usuarios.infrastructure.out.feignclients.IRestaurantEmployeeFeignClients;
import com.pragma.usuarios.infrastructure.out.feignclients.dto.request.RestaurantEmployeeRequestDto;
import com.pragma.usuarios.infrastructure.out.feignclients.mapper.request.IRestaurantEmployeeDtoMapper;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class RestaurantEmployeeFeignAdapter implements IRestaurantEmployeeFeignClientPort {

    private final IRestaurantEmployeeFeignClients restaurantEmployeeFeignClients;
    private final IRestaurantEmployeeDtoMapper restaurantEmployeeDtoMapper;

    @Override
    public void saveRestaurantEmployee(RestaurantEmployeeModel restaurantEmployeeModel) {
        RestaurantEmployeeRequestDto restaurantEmployeeRequestDto = restaurantEmployeeDtoMapper.toRestaurantEmployeeRequestDto(restaurantEmployeeModel);
        restaurantEmployeeFeignClients.saveRestaurantEmployee(restaurantEmployeeRequestDto);
    }
}
