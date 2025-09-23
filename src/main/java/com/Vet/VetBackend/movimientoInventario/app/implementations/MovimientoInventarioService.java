package com.Vet.VetBackend.movimientoInventario.app.implementations;

import com.Vet.VetBackend.movimientoInventario.app.services.IMovimientoInventarioService;
import com.Vet.VetBackend.movimientoInventario.domain.MovimientoInventario;
import com.Vet.VetBackend.movimientoInventario.repo.IMovimientoInventarioRepository;
import com.Vet.VetBackend.movimientoInventario.web.dto.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MovimientoInventarioService implements IMovimientoInventarioService {

    private final IMovimientoInventarioRepository movimientoInventarioRepository;

    public MovimientoInventarioService(IMovimientoInventarioRepository movimientoInventarioRepository) {
        this.movimientoInventarioRepository = movimientoInventarioRepository;
    }

    private MovimientoInventario_Salida toDto(MovimientoInventario movimiento) {
        if (movimiento == null) return null;
        MovimientoInventario_Salida dto = new MovimientoInventario_Salida();
        dto.setId(movimiento.getId());
        dto.setTipo(movimiento.getTipo().name());
        dto.setFecha(movimiento.getFecha());
        dto.setObservacion(movimiento.getObservacion());
        dto.setAlmacenId(movimiento.getAlmacenId());
        dto.setUsuarioId(movimiento.getUsuarioId());
        return dto;
    }

    @Override
    public MovimientoInventario_Salida crear(MovimientoInventario_Guardar dto) {
        MovimientoInventario nuevo = new MovimientoInventario();
        nuevo.setFecha(dto.getFecha());
        nuevo.setObservacion(dto.getObservacion());
        nuevo.setTipo(MovimientoInventario.Status.ENTRADA);
        nuevo.setAlmacenId(dto.getAlmacenId());
        nuevo.setUsuarioId(dto.getUsuarioId());
        return toDto(movimientoInventarioRepository.save(nuevo));
    }

    @Override
    public MovimientoInventario_Salida editar(MovimientoInventario_Modificar dto) {
        Optional<MovimientoInventario> existente = movimientoInventarioRepository.findById(dto.getId());
        if (existente.isPresent()) {
            MovimientoInventario entidad = existente.get();
            entidad.setFecha(dto.getFecha());
            entidad.setObservacion(dto.getObservacion());
            entidad.setTipo(MovimientoInventario.Status.valueOf(dto.getTipo()));
            entidad.setAlmacenId(dto.getAlmacenId());
            entidad.setUsuarioId(dto.getUsuarioId());
            return toDto(movimientoInventarioRepository.save(entidad));
        }
        return null;
    }

    @Override
    public MovimientoInventario_Salida cambiarTipo(MovimientoInventarioCambiarTipo dto) {
        MovimientoInventario movimiento = movimientoInventarioRepository.findById(dto.getId())
                .orElseThrow(() -> new RuntimeException("Movimiento no encontrado"));
        movimiento.setTipo(MovimientoInventario.Status.valueOf(dto.getTipo()));
        return toDto(movimientoInventarioRepository.save(movimiento));
    }

    @Override
    public void eliminarPorId(Integer id) {
        movimientoInventarioRepository.deleteById(id);
    }

    @Override
    public List<MovimientoInventario_Salida> obtenerTodos() {
        return movimientoInventarioRepository.findAll()
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public Page<MovimientoInventario_Salida> obtenerTodosPaginados(Pageable pageable) {
        Page<MovimientoInventario> page = movimientoInventarioRepository.findAll(pageable);
        List<MovimientoInventario_Salida> dtos = page.getContent().stream()
                .map(this::toDto)
                .collect(Collectors.toList());
        return new PageImpl<>(dtos, pageable, page.getTotalElements());
    }

    @Override
    public MovimientoInventario_Salida obtenerPorId(Integer id) {
        return movimientoInventarioRepository.findById(id)
                .map(this::toDto)
                .orElse(null);
    }

    @Override
    public List<MovimientoInventario_Salida> obtenerPorAlmacenId(Integer id) {
        return List.of(); // Placeholder para lógica futura
    }

    @Override
    public List<MovimientoInventario_Salida> obtenerPorUsuarioId(Integer id) {
        return List.of(); // Placeholder para lógica futura
    }
}
