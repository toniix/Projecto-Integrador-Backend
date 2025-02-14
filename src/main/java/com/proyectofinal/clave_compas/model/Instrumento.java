package com.proyectofinal.clave_compas.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Instrumento {
    @Id
    private Long id_instrumento;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "id_imagen")
    private List<Imagen> imagenes;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "id_reserva")
    private List<Reserva> reservas;

    @Column
    private String nombre;

    @Column
    private String modelo;

    @Column
    private Integer anio;

    @Column
    private Integer stock;

    @Column
    private String descripcion;


}
