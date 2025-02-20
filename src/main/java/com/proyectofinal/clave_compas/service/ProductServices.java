package com.proyectofinal.clave_compas.service;

import com.proyectofinal.clave_compas.bd.clavecompas.entities.CategoryEntity;
import com.proyectofinal.clave_compas.bd.clavecompas.entities.ProductEntity;

import com.proyectofinal.clave_compas.bd.clavecompas.repositories.CategoryRepository;
import com.proyectofinal.clave_compas.bd.clavecompas.repositories.ProductRepository;


import com.proyectofinal.clave_compas.exception.NotValidCategory;
import com.proyectofinal.clave_compas.exception.ProductAlreadyOnRepositoryException;
import com.proyectofinal.clave_compas.mappers.ProductMapper;
import com.proyectofinal.clave_compas.service.dto.ProductDTO;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class ProductServices {


    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final ImageServices imageServices;

    public Page<ProductDTO> getPaginateProducts(int page, int size ) {
        Pageable pageable = PageRequest.of(page, size);
        Page<ProductEntity> products = productRepository.findAll(pageable);
        return ProductMapper.INSTANCE.toDTOs(products);
    }
    @Transactional(transactionManager = "txManagerClavecompas", propagation = Propagation.REQUIRED, rollbackFor = {Exception.class, SQLException.class})
    public ProductEntity saveProduct(ProductDTO productDTO) throws ProductAlreadyOnRepositoryException {
        Optional.ofNullable(productDTO.name())
                .flatMap(productRepository::findByName)
                .ifPresent(p -> {
                        throw new ProductAlreadyOnRepositoryException("El producto: "+productDTO.name()+ " ya existe en base de datos");
                });

        CategoryEntity categoryEntity = categoryRepository.findByIdCategory(productDTO.idCategory())
                .orElseThrow(() -> new NotValidCategory("Categoria no disponible"));
        ProductEntity productEntity = ProductMapper.INSTANCE.toEntity(productDTO);

        // FIXME: se reemplaza por un mapper para mas simplicidad
        /*instrument.setTypeInstrument(typeInstrument);
        instrument.setYear(Year.now().getValue());
        instrument.setName(instrumentDto.getName());
        instrument.setDescription(instrumentDto.getDescription());
        instrument.setStock(instrumentDto.getStock());
        instrument.setModel(instrumentDto.getModel());*/

        ProductEntity productSaved = productRepository.save(productEntity);
        imageServices.saveImages(productSaved.getIdProduct(),productDTO.imageUrls());
        return productSaved;
    }

}
