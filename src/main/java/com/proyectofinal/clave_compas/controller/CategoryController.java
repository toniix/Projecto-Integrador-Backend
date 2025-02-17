package com.proyectofinal.clave_compas.controller;


import com.proyectofinal.clave_compas.controller.responses.CategoryResponse;
import com.proyectofinal.clave_compas.service.CategoryService;
import com.proyectofinal.clave_compas.service.dto.CategoryDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value ="categories")
public class CategoryController {
    private final CategoryService categoryService;

    @GetMapping
    public ResponseEntity<CategoryResponse> findAll() {
        List<CategoryDTO> categories = categoryService.getAllCategories();
        CategoryResponse categoryResponse = new CategoryResponse(categories);
        return ResponseEntity.ok(categoryResponse);
    }
}
