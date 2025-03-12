package com.pragma.usuarios.domain.spi;

import com.pragma.usuarios.domain.model.UserModel;

public interface IUserPersistencePort {

    UserModel saveUser(UserModel userModel);

    UserModel getUserById(Long userIdentity);

    void deleteUserById(Long userIdentity);
}
