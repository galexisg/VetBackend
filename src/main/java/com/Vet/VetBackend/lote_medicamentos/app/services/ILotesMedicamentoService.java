package com.Vet.VetBackend.lote_medicamentos.app.services;

import com.Vet.VetBackend.lote_medicamentos.domain.Lotes_medicamentos;
import com.Vet.VetBackend.lote_medicamentos.web.dto.LoteMedicamento_Salida;
import com.Vet.VetBackend.lote_medicamentos.web.dto.LoteMedicamentos_Actualizar;
import com.Vet.VetBackend.lote_medicamentos.web.dto.LoteMedicamentos_Guardar;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

public interface ILotesMedicamentoService {
    List<LoteMedicamento_Salida> obtenerTodos();

    Page<LoteMedicamento_Salida> obtenerTodosPaginados(Pageable pageable);

    LoteMedicamento_Salida obtenerPorId(Integer id);

    LoteMedicamento_Salida crear(LoteMedicamentos_Guardar loteMedicamentosGuardar);

    LoteMedicamento_Salida editar(LoteMedicamentos_Actualizar loteMedicamentosActualizar);

    void eliminarPorId(Integer id);


    List<LoteMedicamento_Salida> obtenerPorMedicamentoId(Integer medicamentoId);

    List<LoteMedicamento_Salida> obtenerPorProveedorId(Integer proveedorId);

    List<LoteMedicamento_Salida> obtenerLotesProximosAVencer();
}
