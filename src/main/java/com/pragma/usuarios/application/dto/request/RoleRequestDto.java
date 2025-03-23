package com.pragma.usuarios.application.dto.request;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RoleRequestDto {
    private String name;
    private String description;
}
