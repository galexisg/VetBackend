package com.Vet.VetBackend.compras.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "compra")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Compra {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Integer id;

        private Integer proveedorId;
//        private Integer usuarioId;
        private LocalDate fecha;
        private String descripcion;
        private double total;
    }
