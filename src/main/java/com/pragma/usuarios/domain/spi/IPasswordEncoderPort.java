package com.pragma.usuarios.domain.spi;

public interface IPasswordEncoderPort {
    String encode(String password);
}
