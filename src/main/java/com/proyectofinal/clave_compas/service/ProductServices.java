package com.proyectofinal.clave_compas.service;

import com.proyectofinal.clave_compas.bd.clavecompas.entities.InstrumentEntity;

import com.proyectofinal.clave_compas.bd.clavecompas.repositories.InstrumentRepository;


import com.proyectofinal.clave_compas.mappers.InstrumentMapper;
import com.proyectofinal.clave_compas.service.dto.InstrumentDTO;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;



@Service
@RequiredArgsConstructor
public class ProductServices {

/*
    //private final ProductRepository productRepository; Aca guardaria si tuviera donde :D
    private  HashMap<Long, InstrumentEntity> productos ;

    public ProductServices(@Autowired ImageServices imageServices) {
        this.productos = new HashMap<>();
    }

    public void saveProduct(InstrumentEntity instrumento, InstrumentoDto instrumentoDto) throws ProductAlreadyOnRepositoryException {
        if (productos.containsKey(instrumento)) throw new ProductAlreadyOnRepositoryException(instrumento.getId_instrumento().toString());
        instrumento.setYear(Year.now().getValue());
        instrumento.setName(instrumentoDto.getNa);
        instrumento.setDescripcion(instrumentoDto.getDescripcion());
        instrumento.setStock(instrumentoDto.getStock());
        instrumento.setModelo(instrumentoDto.getModelo());
        productos.put(instrumento.getId_instrumento(), instrumento); // <-- NO OLVIDAR CAMBIAR AL SAVE EN EL FUTURO
    }*/
    private final InstrumentRepository instrumentRepository;

    public Page<InstrumentDTO> getAllInstruments(int page, int size ) {
        Pageable pageable = PageRequest.of(page, size);
        Page<InstrumentEntity> instruments = instrumentRepository.findAll(pageable);
        return InstrumentMapper.INSTANCE.toDTOs(instruments);
    }

}
