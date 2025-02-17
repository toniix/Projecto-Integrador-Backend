package com.proyectofinal.clave_compas.bd.clavecompas.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;
import lombok.experimental.FieldDefaults;


import java.io.Serializable;
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "user", schema = "clavecompas")
public class User implements Serializable {
    @Id
    @Column(name = "user_id")
    int userId;

    @Column(name = "user_code")
    String userCode;
}
