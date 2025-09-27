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

    private MovimientoInventario_Salida toDto(MovimientoInventario movimientoInventario) {
        if (movimientoInventario == null) return null;

        MovimientoInventario_Salida dto = new MovimientoInventario_Salida();
        dto.setId(movimientoInventario.getId());
        dto.setTipo(movimientoInventario.getTipo().name());
        dto.setFecha(movimientoInventario.getFecha());
        dto.setObservacion(movimientoInventario.getObservacion());

        return dto;
    }

    @Override
    public MovimientoInventario_Salida crear(MovimientoInventario_Guardar dto) {
        MovimientoInventario entidad = new MovimientoInventario();
        entidad.setFecha(dto.getFecha());
        entidad.setObservacion(dto.getObservacion());
        entidad.setTipo(MovimientoInventario.Status.valueOf(dto.getTipo()));
        MovimientoInventario guardado = movimientoInventarioRepository.save(entidad);
        return toDto(guardado);
    }

    @Override
    public MovimientoInventario_Salida editar(MovimientoInventario_Modificar dto) {
        Optional<MovimientoInventario> movimientoExistente = movimientoInventarioRepository.findById(dto.getId());
        if (movimientoExistente.isPresent()) {
            MovimientoInventario entidad = movimientoExistente.get();
            entidad.setFecha(dto.getFecha());
            entidad.setObservacion(dto.getObservacion());
            entidad.setTipo(MovimientoInventario.Status.valueOf(dto.getTipo()));
            MovimientoInventario actualizado = movimientoInventarioRepository.save(entidad);
            return toDto(actualizado);
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
        return List.of(); // Eliminado: lógica de Almacén
    }

    @Override
    public List<MovimientoInventario_Salida> obtenerPorUsuarioId(Integer id) {
        return List.of(); // Placeholder si decides agregar Usuario luego
    }
}
