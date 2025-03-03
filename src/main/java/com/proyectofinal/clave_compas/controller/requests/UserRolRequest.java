package com.proyectofinal.clave_compas.controller.requests;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.util.Set;

@Getter
@Setter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserRolRequest {
    Long idUser;
    Set<Long> idsRol;
}
