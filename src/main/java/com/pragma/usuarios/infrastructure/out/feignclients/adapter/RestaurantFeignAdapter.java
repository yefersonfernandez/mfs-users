package com.pragma.usuarios.infrastructure.out.feignclients.adapter;

import com.pragma.usuarios.domain.model.RestaurantModel;
import com.pragma.usuarios.domain.spi.IRestaurantFeignClientPort;
import com.pragma.usuarios.infrastructure.out.feignclients.IRestaurantFeignClients;
import com.pragma.usuarios.infrastructure.out.feignclients.mapper.response.IRestaurantDtoMapper;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@RequiredArgsConstructor
public class RestaurantFeignAdapter implements IRestaurantFeignClientPort {

    private final IRestaurantFeignClients restaurantFeignClients;
    private final IRestaurantDtoMapper restaurantDtoMapper;

    @Override
    public Optional<RestaurantModel> getRestaurantByOwnerId(Long ownerId) {
        return Optional.ofNullable(restaurantFeignClients.getRestaurantByOwnerId(ownerId))
                .map(restaurantDtoMapper::toRestaurantModel);
    }
}
