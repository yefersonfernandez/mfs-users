package com.pragma.usuarios.infrastructure.out.jpa.adapter;

import com.pragma.usuarios.domain.model.UserModel;
import com.pragma.usuarios.domain.spi.IUserPersistencePort;
import com.pragma.usuarios.infrastructure.out.jpa.entity.UserEntity;
import com.pragma.usuarios.infrastructure.out.jpa.mapper.IUserEntityMapper;
import com.pragma.usuarios.infrastructure.out.jpa.repository.IUserRepository;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@RequiredArgsConstructor
public class UserJpaAdapter implements IUserPersistencePort {

    private final IUserRepository userRepository;
    private final IUserEntityMapper userEntityMapper;

    @Override
    public void saveUser(UserModel userModel) {
        UserEntity userEntity = userEntityMapper.toUserEntity(userModel);
        userRepository.save(userEntity);
    }

    @Override
    public UserModel getUserById(Long userId) {
        Optional<UserEntity> userEntity = userRepository.findById(userId);
        return userEntity.map(userEntityMapper::toUserModel).orElse(null);
    }

}
