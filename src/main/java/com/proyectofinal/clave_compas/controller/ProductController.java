package com.proyectofinal.clave_compas.controller;



import com.proyectofinal.clave_compas.bd.clavecompas.entities.ProductEntity;
import com.proyectofinal.clave_compas.bd.clavecompas.repositories.ProductRepository;
import com.proyectofinal.clave_compas.controller.responses.GlobalResponse;
import com.proyectofinal.clave_compas.service.ImageServices;
import com.proyectofinal.clave_compas.service.ProductServices;
import com.proyectofinal.clave_compas.service.dto.ProductDTO;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductServices productServices;

    @GetMapping
    public ResponseEntity<GlobalResponse> findAll(@RequestParam int page, @RequestParam int pageSize) {
        GlobalResponse gres = GlobalResponse.builder()
                .statusCode(HttpStatus.OK.value())
                .message("Operación exitosa")
                .response(productServices.getPaginateProducts(page, pageSize))
                .build();
        return ResponseEntity.ok(gres);
    }
    
    @PostMapping
    private ResponseEntity<GlobalResponse> saveProduct(@Validated @RequestBody ProductDTO productDto)  {
        GlobalResponse gres = GlobalResponse.builder()
                .statusCode(HttpStatus.OK.value())
                .message("Operación exitosa")
                .response(productServices.saveProduct(productDto))
                .build();

            return ResponseEntity.ok(gres);

    }

    @GetMapping("/{idProduct}")
    public ResponseEntity<GlobalResponse> getProductById(@PathVariable Integer idProduct) {
        GlobalResponse gres = GlobalResponse.builder()
                .statusCode(HttpStatus.OK.value())
                .message("Operación exitosa")
                .response(productServices.getProductById(idProduct))
                .build();
        return ResponseEntity.ok(gres);

    }

    @DeleteMapping("/{idProduct}")
    public ResponseEntity<GlobalResponse> deleteProductById(@PathVariable Integer idProduct) {
        productServices.deleteProductById(idProduct);
        GlobalResponse gres = GlobalResponse.builder()
                .statusCode(HttpStatus.OK.value())
                .message("Operación exitosa")
                .build();
        return ResponseEntity.ok(gres);

    }
}
