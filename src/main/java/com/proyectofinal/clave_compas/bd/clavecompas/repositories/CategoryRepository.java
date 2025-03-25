package com.proyectofinal.clave_compas.bd.clavecompas.repositories;

import com.proyectofinal.clave_compas.bd.clavecompas.entities.CategoryEntity;
import com.proyectofinal.clave_compas.bd.clavecompas.entities.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository extends JpaRepository<CategoryEntity,Integer> {
    public List<CategoryEntity> findAll();
    public Optional<CategoryEntity> findByIdCategory(Integer idCategory);
    Page<CategoryEntity> findAll(Pageable pageable);
}
