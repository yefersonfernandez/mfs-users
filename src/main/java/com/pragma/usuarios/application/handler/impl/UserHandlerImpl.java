package com.pragma.usuarios.application.handler.impl;

import com.pragma.usuarios.application.dto.request.UserRequestDto;
import com.pragma.usuarios.application.dto.response.UserResponseDto;
import com.pragma.usuarios.application.handler.IUserHandler;
import com.pragma.usuarios.application.mapper.response.IRoleResponseMapper;
import com.pragma.usuarios.application.mapper.request.IUserRequestMapper;
import com.pragma.usuarios.application.mapper.response.IUserResponseMapper;
import com.pragma.usuarios.domain.api.IRoleServicePort;
import com.pragma.usuarios.domain.api.IUserServicePort;
import com.pragma.usuarios.domain.model.UserModel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
@Transactional
public class UserHandlerImpl implements IUserHandler {

    private final IUserServicePort userServicePort;
    private final IRoleServicePort roleServicePort;
    private final IUserRequestMapper userRequestMapper;
    private final IUserResponseMapper userResponseMapper;
    private final IRoleResponseMapper roleResponseMapper;

    @Override
    public void saveUser(UserRequestDto userRequestDto) {
        UserModel userModel = userRequestMapper.toUserModel(userRequestDto);
        userServicePort.saveUser(userModel);
    }

    @Override
    public UserResponseDto getUserById(Long userId) {
        return userResponseMapper.toUserResponse(userServicePort.getUserById(userId));
    }


    @Override
    public void deleteUserById(Long userId) {
        userServicePort.deleteUserById(userId);
    }
}
