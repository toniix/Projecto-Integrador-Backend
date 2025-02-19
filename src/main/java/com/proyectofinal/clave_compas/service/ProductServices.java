package com.proyectofinal.clave_compas.service;

import com.proyectofinal.clave_compas.bd.clavecompas.entities.CategoryEntity;
import com.proyectofinal.clave_compas.bd.clavecompas.entities.ProductEntity;

import com.proyectofinal.clave_compas.bd.clavecompas.repositories.CategoryRepository;
import com.proyectofinal.clave_compas.bd.clavecompas.repositories.ProductRepository;


import com.proyectofinal.clave_compas.exception.ProductAlreadyOnRepositoryException;
import com.proyectofinal.clave_compas.mappers.ProductMapper;
import com.proyectofinal.clave_compas.service.dto.ProductDTO;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
@RequiredArgsConstructor
public class ProductServices {


    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    public Page<ProductDTO> getAllProducts(int page, int size ) {
        Pageable pageable = PageRequest.of(page, size);
        Page<ProductEntity> instruments = productRepository.findAll(pageable);
        return ProductMapper.INSTANCE.toDTOs(instruments);
    }

    public void saveProduct( ProductDTO productDTO) throws ProductAlreadyOnRepositoryException {
        Optional.ofNullable(productDTO.name())
                .flatMap(productRepository::findByName)
                .ifPresent(p -> {
                    try {
                        throw new ProductAlreadyOnRepositoryException(productDTO.name());
                    } catch (ProductAlreadyOnRepositoryException e) {
                        throw new RuntimeException(e);
                    }
                });

        CategoryEntity categoryEntity = categoryRepository.findByIdCategory(productDTO.idCategory())
                .orElseThrow(() -> new RuntimeException("Categoria no disponibleo"));
        ProductEntity productEntity = ProductMapper.INSTANCE.toEntity(productDTO);

        // FIXME: se reemplaza por un mapper para mas simplicidad
        /*instrument.setTypeInstrument(typeInstrument);
        instrument.setYear(Year.now().getValue());
        instrument.setName(instrumentDto.getName());
        instrument.setDescription(instrumentDto.getDescription());
        instrument.setStock(instrumentDto.getStock());
        instrument.setModel(instrumentDto.getModel());*/

        productRepository.save(productEntity);
    }

}
