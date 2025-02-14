package com.proyectofinal.clave_compas.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor

public class Usuario {
    @Id
    private Long id_user;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "id_reserva")
    private List<Reserva> reservas;

    @Column
    private String nombre;

    @Column
    private String apellido;

    @Column
    private String documento;

    @Column
    private String email;

    @Column
    private String telefono;

    @Column
    private String direccion;

    @Column
    private String localidad;

    @Column
    private boolean admin;

}
