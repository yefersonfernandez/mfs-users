package com.pragma.usuarios.infrastructure.out.feignclients;

import com.pragma.usuarios.infrastructure.out.feignclients.dto.response.RestaurantResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "${clients.restaurants.name}", url = "${clients.restaurants.base-url}")
public interface IRestaurantFeignClients {

    @GetMapping("/{id}")
    RestaurantResponseDto getRestaurantByOwnerId(@PathVariable(value = "id") Long ownerId);

}
