package com.Vet.VetBackend.movimientoInventario.web.dto;

import com.Vet.VetBackend.almacen.web.dto.Almacen_Salida;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.internal.util.privilegedactions.LoadClass;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
public class MovimientoInventario_Salida implements Serializable {
    private Integer id;

    private LocalDateTime fecha;

    private String tipo;

    private String observacion;

    private Almacen_Salida almacen;

//    private UsuarioSalida usuario;
}
