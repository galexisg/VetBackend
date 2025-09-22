package com.Vet.VetBackend.movimientoInventario.app.implementations;

import com.Vet.VetBackend.almacen.domain.Almacen;
import com.Vet.VetBackend.almacen.repo.IAlmacenRepository;
import com.Vet.VetBackend.movimientoInventario.app.services.IMovimientoInventarioService;
import com.Vet.VetBackend.movimientoInventario.domain.MovimientoInventario;
import com.Vet.VetBackend.movimientoInventario.repo.IMovimientoInventarioRepository;
import com.Vet.VetBackend.movimientoInventario.web.dto.MovimientoInventarioCambiarTipo;
import com.Vet.VetBackend.movimientoInventario.web.dto.MovimientoInventario_Guardar;
import com.Vet.VetBackend.movimientoInventario.web.dto.MovimientoInventario_Modificar;
import com.Vet.VetBackend.movimientoInventario.web.dto.MovimientoInventario_Salida;
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
    private final IAlmacenRepository almacenRepository;
    // Agrega el repositorio de Usuario si lo necesitas
    // private final IUsuarioRepository usuarioRepository;

    // Inyección de dependencias a través del constructor
    public MovimientoInventarioService(
            IMovimientoInventarioRepository movimientoInventarioRepository,
            IAlmacenRepository almacenRepository
    ) {
        this.movimientoInventarioRepository = movimientoInventarioRepository;
        this.almacenRepository = almacenRepository;
    }

    private MovimientoInventario_Salida toDto(MovimientoInventario movimientoInventario) {
        if (movimientoInventario == null) return null;
        MovimientoInventario_Salida dto = new MovimientoInventario_Salida();
        dto.setId(movimientoInventario.getId());
        // Conversión del Enum a String
        dto.setTipo(movimientoInventario.getTipo().name());
        dto.setFecha(movimientoInventario.getFecha());
        dto.setObservacion(movimientoInventario.getObservacion());

        // Relación de Almacen (descomentada porque existe en la BD)
        //dto.setAlmacen(movimientoInventario.getAlmacen());

        // Relación de Usuario (comentada porque no existe en la BD)
        // dto.setUsuario(movimientoInventario.getUsuario());

        return dto;
    }

    @Override
    public List<MovimientoInventario_Salida> obtenerTodos() {
        return movimientoInventarioRepository.findAll().stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public Page<MovimientoInventario_Salida> obtenerTodosPaginados(Pageable pageable) {
        Page<MovimientoInventario> page = movimientoInventarioRepository.findAll(pageable);
        List<MovimientoInventario_Salida> dtos = page.getContent().stream()
                .map(this::toDto)
                .collect(Collectors.toList());
        return new PageImpl<>(dtos, page.getPageable(), page.getTotalElements());
    }

    @Override
    public MovimientoInventario_Salida obtenerPorId(Integer id) {
        return movimientoInventarioRepository.findById(id)
                .map(this::toDto)
                .orElse(null);
    }

    @Override
    public List<MovimientoInventario_Salida> obtenerPorAlmacenId(Integer id) {
        return movimientoInventarioRepository.findByAlmacenId(id).stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<MovimientoInventario_Salida> obtenerPorUsuarioId(Integer id) {
        return movimientoInventarioRepository.findByUsuarioId(id).stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public MovimientoInventario_Salida crear(MovimientoInventario_Guardar dto) {
        MovimientoInventario nuevoMovimiento = new MovimientoInventario();
        nuevoMovimiento.setFecha(dto.getFecha());
        nuevoMovimiento.setObservacion(dto.getObservacion());
        nuevoMovimiento.setTipo(MovimientoInventario.Status.ENTRADA);

        // Manejo de la relación con Almacen
        Almacen almacen = almacenRepository.findById(dto.getAlmacenId())
                .orElseThrow(() -> new RuntimeException("Almacén no encontrado"));
        nuevoMovimiento.setAlmacen(almacen);

        // Relación con Usuario
        // Usuario usuario = usuarioRepository.findById(dto.getUsuarioId())
        //         .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        // nuevoMovimiento.setUsuario(usuario);

        MovimientoInventario guardado = movimientoInventarioRepository.save(nuevoMovimiento);
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

            // Manejo de la relación con Almacen
            Almacen almacen = almacenRepository.findById(dto.getAlmacenId())
                    .orElseThrow(() -> new RuntimeException("Almacén no encontrado"));
            entidad.setAlmacen(almacen);

            // Relación con Usuario
            // Usuario usuario = usuarioRepository.findById(dto.getUsuarioId())
            //         .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
            // entidad.setUsuario(usuario);

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
}