package com.pragma.usuarios.infrastructure.out.jpa.repository;

import com.pragma.usuarios.infrastructure.out.jpa.entity.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IRoleRepository extends JpaRepository<RoleEntity, Long> {

}
