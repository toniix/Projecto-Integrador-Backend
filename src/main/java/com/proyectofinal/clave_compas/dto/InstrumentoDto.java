package com.proyectofinal.clave_compas.dto;

import com.proyectofinal.clave_compas.model.Instrumento;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class InstrumentoDto {

    private String nombre;
    private String descripcion;
    private Integer stock;
    private String modelo;


    public boolean isValid() {
        return !(nombre.isEmpty() && descripcion.isEmpty() && stock < 0 && modelo.isEmpty());
    }


    public Instrumento toEntity() {
        return new Instrumento();
    }
}

