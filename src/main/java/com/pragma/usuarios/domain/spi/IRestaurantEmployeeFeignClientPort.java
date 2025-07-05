package com.pragma.usuarios.domain.spi;

import com.pragma.usuarios.domain.model.RestaurantEmployeeModel;

public interface IRestaurantEmployeeFeignClientPort {
    void saveRestaurantEmployee(RestaurantEmployeeModel restaurantEmployeeModel);
}
