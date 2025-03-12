package com.pragma.usuarios.application.handler;

import com.pragma.usuarios.application.dto.request.UserRequestDto;
import com.pragma.usuarios.application.dto.response.UserResponseDto;
import com.pragma.usuarios.domain.model.UserModel;

import java.util.List;

public interface IUserHandler {

    void saveUser(UserRequestDto userRequestDto);

    UserResponseDto getUserById(Long userId);

    void deleteUserById(Long userId);
}
