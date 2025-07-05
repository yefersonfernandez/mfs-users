package com.pragma.usuarios.domain.spi;

import com.pragma.usuarios.domain.model.RestaurantModel;

import java.util.Optional;

public interface IRestaurantFeignClientPort {
    Optional<RestaurantModel> getRestaurantByOwnerId(Long userId);
}
