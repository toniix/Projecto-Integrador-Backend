package com.proyectofinal.clave_compas.controller.responses;

import com.proyectofinal.clave_compas.service.dto.UserDTO;

import java.util.Set;

public record LoginResponse(
        UserDTO user,
        Set<String> roles,
        String token
) {
}
