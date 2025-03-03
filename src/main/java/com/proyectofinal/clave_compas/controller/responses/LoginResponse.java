package com.proyectofinal.clave_compas.controller.responses;

import com.proyectofinal.clave_compas.service.dto.UserDTO;

public record LoginResponse(
        UserDTO user,
        String token
) {
}
