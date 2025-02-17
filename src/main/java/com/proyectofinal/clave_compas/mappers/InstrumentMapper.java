package com.proyectofinal.clave_compas.mappers;

import com.proyectofinal.clave_compas.bd.clavecompas.entities.ImageEntity;
import com.proyectofinal.clave_compas.bd.clavecompas.entities.InstrumentEntity;
import com.proyectofinal.clave_compas.service.dto.InstrumentDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.List;
import java.util.stream.Collectors;

@Mapper
public interface InstrumentMapper {

    InstrumentMapper INSTANCE = Mappers.getMapper(InstrumentMapper.class);

    @Mapping(source = "category.idCategory", target = "idCategory")
    @Mapping(source = "images", target = "imageUrls", qualifiedByName = "mapImagesToUrls")
    InstrumentDTO toDTO(InstrumentEntity entity);

    @Mapping(source = "idCategory", target = "category.idCategory")
    InstrumentEntity toEntity(InstrumentDTO dto);

    @Named("mapImagesToUrls") // Agregar Named aquí
    default List<String> mapImagesToUrls(List<ImageEntity> images) {
        if (images == null) {
            return List.of();
        }
        return images.stream()
                .map(ImageEntity::getImageUrl)
                .collect(Collectors.toList());
    }

    default Page<InstrumentDTO> toDTOs(Page<InstrumentEntity> entities) {
        List<InstrumentDTO> dtos = entities.stream()
                .map(this::toDTO)  // Usamos la conversión individual
                .collect(Collectors.toList());

        return new PageImpl<>(dtos, entities.getPageable(), entities.getTotalElements());
    }
}
