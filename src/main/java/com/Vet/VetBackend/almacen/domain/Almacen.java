package com.Vet.VetBackend.almacen.domain;

import com.Vet.VetBackend.inventario.domain.Inventario;
import jakarta.persistence.*;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "almacen")
public class Almacen {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(length = 80)
    private String nombre;

    @Column(length = 200)
    private String ubicacion;

//    @Column(columnDefinition = "BIT(1)")
    private Boolean activo;

//    @OneToMany(mappedBy = "almacen")
//    private List<Inventario> inventario;

}
