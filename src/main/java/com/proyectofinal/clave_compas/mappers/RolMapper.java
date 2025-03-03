package com.proyectofinal.clave_compas.mappers;

import com.proyectofinal.clave_compas.bd.clavecompas.entities.CategoryEntity;
import com.proyectofinal.clave_compas.bd.clavecompas.entities.RolEntity;
import com.proyectofinal.clave_compas.bd.clavecompas.entities.UserEntity;
import com.proyectofinal.clave_compas.service.dto.CategoryDTO;
import com.proyectofinal.clave_compas.service.dto.RolDTO;
import com.proyectofinal.clave_compas.service.dto.UserDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface RolMapper {
    RolMapper INSTANCE = Mappers.getMapper(RolMapper.class);

    RolDTO toDTO(RolEntity rolEntity);

    RolEntity toEntity(RolDTO rolDTO);

    List<RolDTO> toDTOs(List<RolEntity> roles);

    List<RolEntity> toEntities(List<RolDTO> rolesDTO);
}
