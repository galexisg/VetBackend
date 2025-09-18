package com.Vet.VetBackend.movimientoDetalle.app.services;

import com.Vet.VetBackend.movimientoDetalle.web.dto.MovimientoDetalle_Guardar;
import com.Vet.VetBackend.movimientoDetalle.web.dto.MovimientoDetalle_Modificar;
import com.Vet.VetBackend.movimientoDetalle.web.dto.MovimientoDetalle_Salida;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IMovimientoDetalleService {
    List<MovimientoDetalle_Salida> obtenerTodos();
    Page<MovimientoDetalle_Salida> obtenerTodosPaginados(Pageable pageable);
    MovimientoDetalle_Salida obtenerPorId(Integer id);
    List<MovimientoDetalle_Salida> obtenerPorMovimientoInventarioId(Integer id);
    List<MovimientoDetalle_Salida> obtenerPorMedicamentoId(Integer id);
    List<MovimientoDetalle_Salida> obtenerPorLoteMedicamento(Integer id);
    MovimientoDetalle_Salida crear(MovimientoDetalle_Guardar movimientoDetalleGuardar);
    MovimientoDetalle_Salida editar(MovimientoDetalle_Modificar movimientoDetalleModificar);
    void eliminarPorId(Integer id);
}