package com.Vet.VetBackend.Medicamento.web.dto;

import com.Vet.VetBackend.Medicamento.domain.Medicamento;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class MedicamentoSalida implements Serializable {
    private Integer id;
    private String nombre;
    private String formafarmacautica;
    private String concentracion;
    private String unidad;
    private String via;
    private Boolean requiereReceta; // ✅ corregido
    private boolean activo;
    private String temperaturaalm;
    private Integer vidautilmeses;

    // --- Método para convertir Entity a DTO ---
    public static MedicamentoSalida fromEntity(Medicamento m) {
        MedicamentoSalida salida = new MedicamentoSalida();
        salida.setId(m.getId());
        salida.setNombre(m.getNombre());
        salida.setFormafarmacautica(m.getFormafarmacautica());
        salida.setConcentracion(m.getConcentracion());
        salida.setUnidad(m.getUnidad());
        salida.setVia(m.getVia());
        salida.setRequiereReceta(m.getRequiereReceta()); // ✅ ahora compatible
        salida.setActivo(m.getActivo());
        salida.setTemperaturaalm(m.getTemperaturaalm());
        salida.setVidautilmeses(m.getVidautilmeses());
        return salida;
    }
}


//package com.Vet.VetBackend.Medicamento.web.dto;
//
//import com.Vet.VetBackend.Medicamento.domain.Medicamento;
//import lombok.Getter;
//import lombok.Setter;
//
//import java.io.Serializable;
//
//@Getter
//@Setter
//public class MedicamentoSalida implements Serializable {
//    private Integer id;
//    private String nombre;
//    private String formafarmacautica;
//    private String concentracion;
//    private String unidad;
//    private String via;
//    private String requiereReceta;
//    private boolean activo;
//    private String temperaturaalm;
//    private Integer vidautilmeses;
//
//    // --- Método para convertir Entity a DTO ---
//    public static MedicamentoSalida fromEntity(Medicamento m) {
//        MedicamentoSalida salida = new MedicamentoSalida();
//        salida.setId(m.getId());
//        salida.setNombre(m.getNombre());
//        salida.setFormafarmacautica(m.getFormafarmacautica());
//        salida.setConcentracion(m.getConcentracion());
//        salida.setUnidad(m.getUnidad());
//        salida.setVia(m.getVia());
//        salida.setRequiereReceta(m.getRequiereReceta());
//        salida.setActivo(m.getActivo());
//        salida.setTemperaturaalm(m.getTemperaturaalm());
//        salida.setVidautilmeses(m.getVidautilmeses());
//        return salida;
//    }
//}
