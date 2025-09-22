package com.Vet.VetBackend.movimientoDetalle.app.implementations;

import com.Vet.VetBackend.Medicamento.domain.Medicamento;
import com.Vet.VetBackend.Medicamento.repo.IMedicamentoRepository;
import com.Vet.VetBackend.almacen.domain.Almacen;
import com.Vet.VetBackend.almacen.repo.IAlmacenRepository;
import com.Vet.VetBackend.lote_medicamentos.domain.Lotes_medicamentos;
import com.Vet.VetBackend.lote_medicamentos.repo.ILotesMedicamentosRepository;
import com.Vet.VetBackend.movimientoDetalle.app.services.IMovimientoDetalleService;
import com.Vet.VetBackend.movimientoDetalle.domain.MovimientoDetalle;
import com.Vet.VetBackend.movimientoDetalle.repo.IMovimientoDetalleRepository;
import com.Vet.VetBackend.movimientoDetalle.web.dto.MovimientoDetalle_Guardar;
import com.Vet.VetBackend.movimientoDetalle.web.dto.MovimientoDetalle_Modificar;
import com.Vet.VetBackend.movimientoDetalle.web.dto.MovimientoDetalle_Salida;
import com.Vet.VetBackend.movimientoInventario.domain.MovimientoInventario;
import com.Vet.VetBackend.movimientoInventario.repo.IMovimientoInventarioRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MovimientoDetalleService implements IMovimientoDetalleService {

    private final IMovimientoDetalleRepository movimientoDetalleRepository;
    private final IMedicamentoRepository medicamentoRepository;
    private final ILotesMedicamentosRepository loteMedicamentoRepository;
    private final IAlmacenRepository almacenRepository;
    private final IMovimientoInventarioRepository movimientoInventarioRepository;

    public MovimientoDetalleService(IMovimientoDetalleRepository movimientoDetalleRepository,
                                    IMedicamentoRepository medicamentoRepository,
                                    ILotesMedicamentosRepository loteMedicamentoRepository,
                                    IAlmacenRepository almacenRepository,
                                    IMovimientoInventarioRepository movimientoInventarioRepository) {
        this.movimientoDetalleRepository = movimientoDetalleRepository;
        this.medicamentoRepository = medicamentoRepository;
        this.loteMedicamentoRepository = loteMedicamentoRepository;
        this.almacenRepository = almacenRepository;
        this.movimientoInventarioRepository = movimientoInventarioRepository;
    }

    private MovimientoDetalle_Salida toDto(MovimientoDetalle movimientoDetalle) {
        if (movimientoDetalle == null) return null;
        MovimientoDetalle_Salida dto = new MovimientoDetalle_Salida();
        dto.setId(movimientoDetalle.getId());
        dto.setCantidad(movimientoDetalle.getCantidad());
        dto.setCostoUnitario(movimientoDetalle.getCostoUnitario());
        dto.setFecha(movimientoDetalle.getFecha());
        // Puedes mapear los campos de las relaciones si los necesitas en el DTO de salida
        //dto.setMedicamentoId(movimientoDetalle.getMedicamento().getId());
        //dto.setAlmacenId(movimientoDetalle.getAlmacen().getId());
        return dto;
    }

    @Override
    public List<MovimientoDetalle_Salida> obtenerTodos() {
        return movimientoDetalleRepository.findAll().stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public Page<MovimientoDetalle_Salida> obtenerTodosPaginados(Pageable pageable) {
        Page<MovimientoDetalle> page = movimientoDetalleRepository.findAll(pageable);
        List<MovimientoDetalle_Salida> dtos = page.getContent().stream()
                .map(this::toDto)
                .collect(Collectors.toList());
        return new PageImpl<>(dtos, page.getPageable(), page.getTotalElements());
    }

    @Override
    public MovimientoDetalle_Salida obtenerPorId(Integer id) {
        return movimientoDetalleRepository.findById(id)
                .map(this::toDto)
                .orElse(null);
    }

    @Override
    public List<MovimientoDetalle_Salida> obtenerPorMovimientoInventarioId(Integer id) {
        return movimientoDetalleRepository.findByMovimientoInventario_Id(id).stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<MovimientoDetalle_Salida> obtenerPorMedicamentoId(Integer id) {
        return movimientoDetalleRepository.findByMedicamento_Id(id).stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<MovimientoDetalle_Salida> obtenerPorLoteMedicamento(Integer id){
        return movimientoDetalleRepository.findByLotesMedicamentos_Id(id).stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public MovimientoDetalle_Salida crear(MovimientoDetalle_Guardar dto) {
        MovimientoDetalle entidad = new MovimientoDetalle();
        entidad.setCantidad(dto.getCantidad());
        entidad.setCostoUnitario(dto.getCostoUnitario());
        entidad.setFecha(dto.getFecha());

        Medicamento medicamento = medicamentoRepository.findById(dto.getMedicamentoId())
                .orElseThrow(() -> new RuntimeException("Medicamento no encontrado"));
        Lotes_medicamentos lote = loteMedicamentoRepository.findById(dto.getLoteMedicamentoId())
                .orElseThrow(() -> new RuntimeException("Lote de Medicamento no encontrado"));
        Almacen almacen = almacenRepository.findById(dto.getAlmacenId())
                .orElseThrow(() -> new RuntimeException("Almacén no encontrado"));
        MovimientoInventario movimientoInventario = movimientoInventarioRepository.findById(dto.getMovimientoInventarioId())
                .orElseThrow(() -> new RuntimeException("Movimiento de inventario no encontrado"));

        entidad.setMedicamento(medicamento);
        entidad.setLoteMedicamento(lote);
        entidad.setAlmacen(almacen);
        entidad.setMovimientoInventario(movimientoInventario);

        MovimientoDetalle guardado = movimientoDetalleRepository.save(entidad);
        return toDto(guardado);
    }

    @Override
    public MovimientoDetalle_Salida editar(MovimientoDetalle_Modificar dto) {
        Optional<MovimientoDetalle> movimientoDetalleExistente = movimientoDetalleRepository.findById(dto.getId());
        if (movimientoDetalleExistente.isPresent()) {
            MovimientoDetalle entidad = movimientoDetalleExistente.get();
            entidad.setCantidad(dto.getCantidad());
            entidad.setCostoUnitario(dto.getCostoUnitario());
            entidad.setFecha(dto.getFecha());

            Medicamento medicamento = medicamentoRepository.findById(dto.getMedicamentoId())
                    .orElseThrow(() -> new RuntimeException("Medicamento no encontrado"));
            Lotes_medicamentos lote = loteMedicamentoRepository.findById(dto.getLoteMedicamentoId())
                    .orElseThrow(() -> new RuntimeException("Lote de Medicamento no encontrado"));
            Almacen almacen = almacenRepository.findById(dto.getAlmacenId())
                    .orElseThrow(() -> new RuntimeException("Almacén no encontrado"));
            MovimientoInventario movimientoInventario = movimientoInventarioRepository.findById(dto.getMovimientoInventarioId())
                    .orElseThrow(() -> new RuntimeException("Movimiento de inventario no encontrado"));

            entidad.setMedicamento(medicamento);
            entidad.setLoteMedicamento(lote);
            entidad.setAlmacen(almacen);
            entidad.setMovimientoInventario(movimientoInventario);

            MovimientoDetalle actualizado = movimientoDetalleRepository.save(entidad);
            return toDto(actualizado);
        }
        return null;
    }

    @Override
    public void eliminarPorId(Integer id) {
        movimientoDetalleRepository.deleteById(id);
    }
}