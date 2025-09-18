package com.Vet.VetBackend.Medicamento.domain;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "Medicamento")


public class Medicamento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Integer id;

    private String nombre;
    private String formafarmacautica;
    private String concentracion;
    private String unidad;
    private String via;
    private String requiereReceta;
    private String activo;
    private String temperaturaalm;
    private Integer  vidautilmeses;
}

