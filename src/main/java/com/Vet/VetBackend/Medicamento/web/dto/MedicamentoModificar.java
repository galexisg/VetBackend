package com.Vet.VetBackend.Medicamento.web.dto;

import lombok.Getter;
import lombok.Setter;
import java.io.Serializable;

@Getter
@Setter
public class MedicamentoModificar implements Serializable {
    private Integer id;
    private String nombre;
    private String formafarmacautica;
    private String concentracion;
    private String unidad;
    private String via;
    private Boolean activo;
    private Boolean requiereReceta; // ✅ corregido
    private String temperaturaalm;
    private Integer vidautilmeses;
}


//package com.Vet.VetBackend.Medicamento.web.dto;
//
//import lombok.Getter;
//import lombok.Setter;
//
//import java.io.Serializable;
//@Getter
//@Setter
//
//
//public class MedicamentoModificar  implements  Serializable{
//    private  Integer id;
//    private String nombre;
//    private String formafarmacautica;
//    private String concentracion;
//    private String unidad;
//    private String via;
//    private Boolean activo;
//    private String requiereReceta;
//    private String temperaturaalm;
//    private Integer  vidautilmeses;
//
//}
//
//
//
