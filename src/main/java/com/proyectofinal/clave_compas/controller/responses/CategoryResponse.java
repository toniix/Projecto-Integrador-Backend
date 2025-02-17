package com.proyectofinal.clave_compas.controller.responses;


import com.proyectofinal.clave_compas.service.dto.CategoryDTO;

import java.util.List;

public record CategoryResponse (List<CategoryDTO> categories){

}
