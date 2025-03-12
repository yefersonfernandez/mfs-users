package com.pragma.usuarios;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class UsuariosApplication {
	private static final Logger logger = LoggerFactory.getLogger(UsuariosApplication.class);

	public static void main(String[] args) {
		logger.info("Iniciando la aplicación Usuarios...");
		SpringApplication.run(UsuariosApplication.class, args);
		logger.info("Aplicación Usuarios iniciada correctamente.");
	}

}
