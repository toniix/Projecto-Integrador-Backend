package com.proyectofinal.clave_compas.controller;


import com.proyectofinal.clave_compas.bd.clavecompas.repositories.CategoryRepository;
import com.proyectofinal.clave_compas.controller.responses.CategoryResponse;
import com.proyectofinal.clave_compas.controller.responses.GlobalResponse;
import com.proyectofinal.clave_compas.service.CategoryService;
import com.proyectofinal.clave_compas.dto.CategoryDTO;
import com.proyectofinal.clave_compas.util.Constants;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value ="categories")
public class CategoryController {
    private final CategoryService categoryService;
    private final CategoryRepository categoryRepository;

    @GetMapping
    public ResponseEntity<GlobalResponse> findAll() {
        List<CategoryDTO> categories = categoryService.getAllCategories();
        CategoryResponse categoryResponse = new CategoryResponse(categories);
        GlobalResponse gres = GlobalResponse.builder()
                .statusCode(HttpStatus.OK.value())
                .message(Constants.MENSAJE_EXITO)
                .response(categoryResponse)
                .build();

        return ResponseEntity.ok(gres);
    }

    @PostMapping
    public ResponseEntity<GlobalResponse> saveCategory(@RequestBody CategoryDTO category) {
        categoryService.saveCategory(category);
        GlobalResponse gres = GlobalResponse.builder()
                .statusCode(HttpStatus.CREATED.value())
                .message(Constants.MENSAJE_EXITO)
                .build();
        return ResponseEntity.ok(gres);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<GlobalResponse> deleteCategory( @PathVariable Integer id) {
        if (!categoryRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Categoría no encontrada");
        }
        categoryRepository.deleteById(id);
        GlobalResponse gres = GlobalResponse.builder()
                .statusCode(HttpStatus.CREATED.value())
                .message(Constants.MENSAJE_EXITO)
                .build();
        return ResponseEntity.ok(gres);
    }

    @GetMapping(value = "admin")
    public ResponseEntity<GlobalResponse> findAll(@RequestParam int page, @RequestParam int pageSize) {
        GlobalResponse gres = GlobalResponse.builder()
                .statusCode(HttpStatus.OK.value())
                .message(Constants.MENSAJE_EXITO)
                .response(categoryService.getPaginateCategories(page, pageSize))
                .build();
        return ResponseEntity.ok(gres);
    }

}
