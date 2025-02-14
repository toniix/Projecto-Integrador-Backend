package com.proyectofinal.clave_compas.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Reserva {
    @Id
    private Long id_reserva;

    @Column
    private Integer cantidad;

    @Column
    private Date fecha_inicio;

    @Column
    private Date fecha_fin;
}
