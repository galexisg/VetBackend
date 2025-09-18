package com.Vet.VetBackend.almacen.domain;

import jakarta.persistence.*;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "Almacen")
public class Almacen {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(length = 80)
    private String nombre;

    @Column(length = 200)
    private String ubicacion;

    private Boolean activo;

//    @OneToMany(mappedBy = "almacen")
//    private List<Inventario> inventario;
}
