package com.pragma.usuarios.infrastructure.out.feignclients.dto.request;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RestaurantEmployeeRequestDto {
    private String restaurantId;
    private String personId;
}