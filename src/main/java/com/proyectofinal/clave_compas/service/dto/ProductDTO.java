package com.proyectofinal.clave_compas.service.dto;

import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.util.List;

public record ProductDTO(
        Integer idProduct,
        @NotBlank
        String name,
        @NotBlank
        String brand,
        @NotBlank
        String model,
        @Min(1900)
        @Max(2100)
        Integer year,
        @Min(1)
        Integer stock,
        String description,
        @DecimalMin("0.01")
        BigDecimal price,
        Boolean available,
        @NotNull
        Integer idCategory,
        List<String> imageUrls
) {
    public ProductDTO(Integer idProduct, String name, String brand, String model, Integer year,
                      Integer stock, String description, BigDecimal price, Boolean available,
                      Integer idCategory) {
        this(idProduct, name, brand, model, year, stock, description, price, available, idCategory, List.of());
    }
}
