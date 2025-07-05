package com.pragma.usuarios.infrastructure.out.feignclients;

import com.pragma.usuarios.infrastructure.out.feignclients.dto.request.RestaurantEmployeeRequestDto;
import org.springframework.web.bind.annotation.PostMapping;

public interface IRestaurantEmployeeFeignClients {

    @PostMapping("/")
    void saveRestaurantEmployee(RestaurantEmployeeRequestDto restaurantEmployeeRequestDto);

}
