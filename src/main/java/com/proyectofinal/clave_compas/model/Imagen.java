package com.proyectofinal.clave_compas.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Imagen {
    @Id
    private Long id_imagen;


    @Column(nullable = false)
    private String url;


}
