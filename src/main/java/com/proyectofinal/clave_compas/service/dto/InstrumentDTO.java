package com.proyectofinal.clave_compas.service.dto;

import java.math.BigDecimal;
import java.util.List;

public record InstrumentDTO(
        Integer idInstrument,
        String name,
        String brand,
        String model,
        Integer year,
        Integer stock,
        String description,
        BigDecimal price,
        Boolean available,
        Integer idCategory,
        List<String> imageUrls
) {}
