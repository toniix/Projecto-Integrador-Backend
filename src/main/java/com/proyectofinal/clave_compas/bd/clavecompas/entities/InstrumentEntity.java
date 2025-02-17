package com.proyectofinal.clave_compas.bd.clavecompas.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "instrument", schema = "clavecompas")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InstrumentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_instrument")
    private Integer idInstrument;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(length = 50)
    private String brand;

    @Column(length = 50)
    private String model;

    private Integer year;

    @Column(nullable = false, columnDefinition = "INT DEFAULT 1")
    private Integer stock;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal price;

    @Column(columnDefinition = "BOOLEAN DEFAULT true")
    private Boolean available;

    @ManyToOne
    @JoinColumn(name = "id_category", nullable = false)
    private CategoryEntity category;

    @OneToMany(mappedBy = "instrument", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ImageEntity> images;
}


