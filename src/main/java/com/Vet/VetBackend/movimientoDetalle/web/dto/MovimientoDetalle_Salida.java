package com.Vet.VetBackend.movimientoDetalle.web.dto;

import com.Vet.VetBackend.Medicamento.web.dto.MedicamentoSalida;
import com.Vet.VetBackend.almacen.domain.Almacen;
import com.Vet.VetBackend.almacen.web.dto.Almacen_Salida;
import com.Vet.VetBackend.lote_medicamentos.web.dto.LoteMedicamento_Salida;
import com.Vet.VetBackend.movimientoInventario.web.dto.MovimientoInventario_Salida;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
public class MovimientoDetalle_Salida implements Serializable {
    private Integer id;

    private double cantidad;

    private double costoUnitario;

    private LocalDateTime fecha;

    private MovimientoInventario_Salida movimientoInventario;

    private MedicamentoSalida medicamento;

    private LoteMedicamento_Salida loteMedicamento;

    private Almacen_Salida almacen;
}
