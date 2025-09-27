package com.Vet.VetBackend.Medicamento.web.dto;

import lombok.Data;
import java.io.Serializable;

@Data
public class MedicamentoGuardar implements Serializable {
    private String nombre;
    private String formafarmacautica;
    private String concentracion;
    private String unidad;
    private String via;
    private Boolean requiereReceta; // ✅ corregido
    private boolean activo;
    private String temperaturaalm;
    private Integer vidautilmeses;
}


//package com.Vet.VetBackend.Medicamento.web.dto;
//
//import lombok.*;
//
//import java.io.Serializable;
//
//@Data
//public class MedicamentoGuardar implements Serializable{
//    private String nombre;
//    private String formafarmacautica;
//    private String concentracion;
//    private String unidad;
//    private String via;
//    private String requiereReceta;
//    private boolean activo;
//    private String temperaturaalm;
//    private Integer  vidautilmeses;
//
//}
//
//
