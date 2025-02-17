package com.proyectofinal.clave_compas.service;

import com.proyectofinal.clave_compas.bd.clavecompas.entities.CategoryEntity;
import com.proyectofinal.clave_compas.bd.clavecompas.repositories.CategoryRepository;
import com.proyectofinal.clave_compas.exception.ResourceNotFoundException;
import com.proyectofinal.clave_compas.mappers.CategoryMapper;
import com.proyectofinal.clave_compas.service.dto.CategoryDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public List<CategoryDTO> getAllCategories(){
        List<CategoryEntity> allCategories = categoryRepository.findAll();
        if(allCategories==null){
            throw new ResourceNotFoundException("No Category found");
        }
        List<CategoryEntity> subCategories = allCategories.stream().filter(categoryEntity -> categoryEntity.getParentCategory()!=null).toList();
        return CategoryMapper.INSTANCE.odontologosToOdontologosDTO(subCategories);
    }
}
