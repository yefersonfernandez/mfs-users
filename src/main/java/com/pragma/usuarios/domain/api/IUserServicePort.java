package com.pragma.usuarios.domain.api;

import com.pragma.usuarios.domain.model.UserModel;

public interface IUserServicePort {

    void saveUser(UserModel userModel);

    UserModel getUserById(Long userId);

}
