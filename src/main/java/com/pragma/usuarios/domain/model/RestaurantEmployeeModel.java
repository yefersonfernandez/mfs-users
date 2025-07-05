package com.pragma.usuarios.domain.model;

public class RestaurantEmployeeModel {
    private Long id;

    private Long restaurantId;

    private Long userId;

    public RestaurantEmployeeModel() {
    }

    public RestaurantEmployeeModel(Long restaurantId, Long userId) {
        this.restaurantId = restaurantId;
        this.userId = userId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(Long restaurantId) {
        this.restaurantId = restaurantId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
