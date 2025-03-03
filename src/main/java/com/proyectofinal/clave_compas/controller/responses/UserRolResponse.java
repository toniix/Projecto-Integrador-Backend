package com.proyectofinal.clave_compas.controller.responses;

import com.proyectofinal.clave_compas.service.dto.RolDTO;
import com.proyectofinal.clave_compas.service.dto.UserDTO;

import java.util.List;

public record UserRolResponse(
        UserDTO user,
        List<RolDTO> roles
) {
}
