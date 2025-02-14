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

public class Tipo_Instrumento {
    @Id
    private Long id_tipo;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "id_instrumento")
    private List<Instrumento> instrumentos;

    @Column
    private String nombre;

}
