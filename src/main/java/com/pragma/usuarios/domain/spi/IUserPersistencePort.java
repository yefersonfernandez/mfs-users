package com.pragma.usuarios.domain.spi;

import com.pragma.usuarios.domain.model.UserModel;

import java.util.Optional;

public interface IUserPersistencePort {

    void saveUser(UserModel userModel);

    Optional<UserModel> getUserById(Long userId);

    Optional<UserModel> getUserByEmail(String email);
}
