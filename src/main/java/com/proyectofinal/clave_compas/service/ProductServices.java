package com.proyectofinal.clave_compas.service;

import com.proyectofinal.clave_compas.dto.InstrumentoDto;
import com.proyectofinal.clave_compas.exception.ProductAlreadyOnRepositoryException;
import com.proyectofinal.clave_compas.model.Instrumento;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Year;
import java.util.HashMap;

@Service
public class ProductServices {


    //private final ProductRepository productRepository; Aca guardaria si tuviera donde :D
    private  HashMap<Long, Instrumento> productos ;

    public ProductServices(@Autowired ImageServices imageServices) {
        this.productos = new HashMap<>();
    }

    public void saveProduct(Instrumento instrumento, InstrumentoDto instrumentoDto) throws ProductAlreadyOnRepositoryException {
        if (productos.containsKey(instrumento)) throw new ProductAlreadyOnRepositoryException(instrumento.getId_instrumento().toString());
        instrumento.setAnio(Year.now().getValue());
        instrumento.setNombre(instrumentoDto.getNombre());
        instrumento.setDescripcion(instrumentoDto.getDescripcion());
        instrumento.setStock(instrumentoDto.getStock());
        instrumento.setModelo(instrumentoDto.getModelo());
        productos.put(instrumento.getId_instrumento(), instrumento); // <-- NO OLVIDAR CAMBIAR AL SAVE EN EL FUTURO
    }
}
