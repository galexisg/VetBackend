package com.Vet.VetBackend.lote_medicamentos.app.implementations;


import com.Vet.VetBackend.Medicamento.domain.Medicamento;
import com.Vet.VetBackend.Medicamento.repo.IMedicamentoRepository;
import com.Vet.VetBackend.lote_medicamentos.domain.Lotes_medicamentos;
import com.Vet.VetBackend.lote_medicamentos.repo.ILotesMedicamentosRepository;
import com.Vet.VetBackend.lote_medicamentos.app.services.ILotesMedicamentoService;
import com.Vet.VetBackend.lote_medicamentos.web.dto.LoteMedicamento_Salida;
import com.Vet.VetBackend.lote_medicamentos.web.dto.LoteMedicamentos_Actualizar;
import com.Vet.VetBackend.lote_medicamentos.web.dto.LoteMedicamentos_Guardar;
import com.Vet.VetBackend.proveedores.domain.Proveedor;
import com.Vet.VetBackend.proveedores.repo.IProveedorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class LotesMedicamentoService implements ILotesMedicamentoService {

    @Autowired
    private ILotesMedicamentosRepository lotesMedicamentosRepository;

    @Autowired
    private IMedicamentoRepository medicamentoRepository;

    @Autowired
    private IProveedorRepository proveedorRepository;

    @Override
    public List<LoteMedicamento_Salida> obtenerTodos() {
        return lotesMedicamentosRepository.findAll().stream()
                .map(this::convertirASalida)
                .collect(Collectors.toList());
    }

    @Override
    public Page<LoteMedicamento_Salida> obtenerTodosPaginados(Pageable pageable) {
        Page<Lotes_medicamentos> page = lotesMedicamentosRepository.findAll(pageable);
        List<LoteMedicamento_Salida> dtoList = page.stream()
                .map(this::convertirASalida)
                .collect(Collectors.toList());
        return new PageImpl<>(dtoList, page.getPageable(), page.getTotalElements());
    }

    @Override
    public LoteMedicamento_Salida obtenerPorId(Integer id) {
        Lotes_medicamentos lote = lotesMedicamentosRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Lote no encontrado"));
        return convertirASalida(lote);
    }

    @Override
    public LoteMedicamento_Salida crear(LoteMedicamentos_Guardar dto) {
        Medicamento medicamento = medicamentoRepository.findById(dto.getMedicamentoId())
                .orElseThrow(() -> new RuntimeException("Medicamento no encontrado"));
        Proveedor proveedor = proveedorRepository.findById(dto.getProveedorId())
                .orElseThrow(() -> new RuntimeException("Proveedor no encontrado"));

        Lotes_medicamentos lote = new Lotes_medicamentos();
        lote.setCodigoLote(dto.getCodigoLote());
        lote.setFechaVencimiento(dto.getFechaVencimiento());
        lote.setMedicamento(medicamento);
        lote.setProveedor(proveedor);
        lote.setObservaciones(dto.getObservaciones());

        return convertirASalida(lotesMedicamentosRepository.save(lote));
    }

    @Override
    public LoteMedicamento_Salida editar(LoteMedicamentos_Actualizar dto) {
        Lotes_medicamentos existente = lotesMedicamentosRepository.findById(dto.getId())
                .orElseThrow(() -> new RuntimeException("Lote no encontrado"));

        Medicamento medicamento = medicamentoRepository.findById(dto.getMedicamentoId())
                .orElseThrow(() -> new RuntimeException("Medicamento no encontrado"));
        Proveedor proveedor = proveedorRepository.findById(dto.getProveedorId())
                .orElseThrow(() -> new RuntimeException("Proveedor no encontrado"));

        existente.setCodigoLote(dto.getCodigoLote());
        existente.setFechaVencimiento(dto.getFechaVencimiento());
        existente.setMedicamento(medicamento);
        existente.setProveedor(proveedor);
        existente.setObservaciones(dto.getObservaciones());

        return convertirASalida(lotesMedicamentosRepository.save(existente));
    }

    @Override
    public void eliminarPorId(Integer id) {
        lotesMedicamentosRepository.deleteById(id);
    }

    @Override
    public List<LoteMedicamento_Salida> obtenerPorMedicamentoId(Integer medicamentoId) {
        return lotesMedicamentosRepository.findByMedicamento_Id(medicamentoId).stream()
                .map(this::convertirASalida)
                .collect(Collectors.toList());
    }

    @Override
    public List<LoteMedicamento_Salida> obtenerPorProveedorId(Integer proveedorId) {
        return lotesMedicamentosRepository.findByProveedor_Id(proveedorId).stream()
                .map(this::convertirASalida)
                .collect(Collectors.toList());
    }

    @Override
    public List<LoteMedicamento_Salida> obtenerLotesProximosAVencer() {
        LocalDate hoy = LocalDate.now();
        LocalDate limite = hoy.plusDays(30);
        Date desde = Date.valueOf(hoy);
        Date hasta = Date.valueOf(limite);

        return lotesMedicamentosRepository.findByFechaVencimientoBetween(desde, hasta).stream()
                .map(this::convertirASalida)
                .collect(Collectors.toList());
    }

    private LoteMedicamento_Salida convertirASalida(Lotes_medicamentos lote) {
        LoteMedicamento_Salida salida = new LoteMedicamento_Salida();
        salida.setId(lote.getId());
        salida.setCodigoLote(lote.getCodigoLote());

        // 🔹 Conversión correcta
        if (lote.getFechaVencimiento() != null) {
            salida.setFechaVencimiento(new java.sql.Date(lote.getFechaVencimiento().getTime()));
        }

        salida.setObservaciones(lote.getObservaciones());

        if (lote.getMedicamento() != null) {
            salida.setMedicamentoId(lote.getMedicamento().getId());
            salida.setMedicamentoNombre(lote.getMedicamento().getNombre());
        }
        if (lote.getProveedor() != null) {
            salida.setProveedorId(lote.getProveedor().getId());
            salida.setProveedorNombre(lote.getProveedor().getNombre());
        }

        return salida;
    }

}
