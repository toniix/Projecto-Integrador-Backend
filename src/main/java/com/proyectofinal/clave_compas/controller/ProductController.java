package com.proyectofinal.clave_compas.controller;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.proyectofinal.clave_compas.bd.clavecompas.entities.ProductEntity;
import com.proyectofinal.clave_compas.exception.NotValidProductData;
import com.proyectofinal.clave_compas.exception.ProductAlreadyOnRepositoryException;
import com.proyectofinal.clave_compas.mappers.ProductMapper;
import com.proyectofinal.clave_compas.service.ImageServices;
import com.proyectofinal.clave_compas.service.ProductServices;
import com.proyectofinal.clave_compas.service.dto.ProductDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;


@RestController
@RequestMapping(value = "products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductServices productServices;
    private final ImageServices imageServices;
    

    @GetMapping
    public ResponseEntity<Page<ProductDTO>> findAll(@RequestParam int page, @RequestParam int pageSize) {
        return ResponseEntity.ok(productServices.getAllProducts(page, pageSize));
    }
    
    @PostMapping
    private ResponseEntity<ProductEntity> registrarProducto(@RequestParam String instrumentDtoJson,
                                                            @RequestParam("file") MultipartFile file) throws NotValidProductData, FileNotFoundException {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            ProductDTO productDto = objectMapper.readValue(instrumentDtoJson, ProductDTO.class);
            //if (!productDto.isValid()) throw new NotValidProductData("Argumentos no validos");
            if (file.isEmpty()) throw new FileNotFoundException();
            ProductEntity productEntity = ProductMapper.INSTANCE.toEntity(productDto);
            productServices.saveProduct(productDto);
            imageServices.saveImage(file, productEntity);
            return ResponseEntity.ok(productEntity);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

    }
}
