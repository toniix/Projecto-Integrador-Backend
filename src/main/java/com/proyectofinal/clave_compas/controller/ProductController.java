package com.proyectofinal.clave_compas.controller;



import com.proyectofinal.clave_compas.bd.clavecompas.entities.ProductEntity;
import com.proyectofinal.clave_compas.service.ImageServices;
import com.proyectofinal.clave_compas.service.ProductServices;
import com.proyectofinal.clave_compas.service.dto.ProductDTO;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductServices productServices;
    private final ImageServices imageServices;
    

    @GetMapping
    public ResponseEntity<Page<ProductDTO>> findAll(@RequestParam int page, @RequestParam int pageSize) {
        return ResponseEntity.ok(productServices.getPaginateProducts(page, pageSize));
    }
    
    @PostMapping
    private ResponseEntity<ProductEntity> saveProduct(@Validated @RequestBody ProductDTO productDto)  {

            ProductEntity productEntity = productServices.saveProduct(productDto);
            return ResponseEntity.ok(productEntity);

    }
}
