package com.Vet.VetBackend.dispensa.app.implementations;

import com.Vet.VetBackend.dispensa.web.dto.Dispensa_Actualizar;
import com.Vet.VetBackend.dispensa.web.dto.Dispensa_Guardar;
import com.Vet.VetBackend.dispensa.web.dto.Dispensa_Salida;
import com.Vet.VetBackend.dispensa.domain.Dispensa;
import com.Vet.VetBackend.dispensa.repo.IDispensaRepository;
import com.Vet.VetBackend.dispensa.app.services.IDispensaService;
import com.Vet.VetBackend.almacen.domain.Almacen;
import com.Vet.VetBackend.almacen.repo.IAlmacenRepository;
import com.Vet.VetBackend.usuarios.domain.Usuario;
import com.Vet.VetBackend.usuarios.repo.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DispensaService implements IDispensaService {

    @Autowired
    private IDispensaRepository repository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private IAlmacenRepository almacenRepository;

    @Override
    @Transactional(readOnly = true)
    public List<Dispensa_Salida> obtenerTodas() {
        return repository.findAll().stream()
                .map(this::convertirASalida)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Dispensa_Salida> obtenerTodasPaginadas(Pageable pageable) {
        Page<Dispensa> page = repository.findAll(pageable);
        List<Dispensa_Salida> dtoList = page.stream()
                .map(this::convertirASalida)
                .collect(Collectors.toList());
        return new PageImpl<>(dtoList, page.getPageable(), page.getTotalElements());
    }

    @Override
    @Transactional(readOnly = true)
    public Dispensa_Salida obtenerPorId(Integer id) {
        Dispensa entidad = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Dispensa no encontrada"));
        return convertirASalida(entidad);
    }

    @Override
    @Transactional
    public Dispensa_Salida crear(Dispensa_Guardar dto) {
        System.out.println("📥 DTO recibido: " + dto);
        Dispensa entidad = new Dispensa();
        entidad.setFecha(dto.getFecha());
        entidad.setCantidad(dto.getCantidad());
        entidad.setPrescripcionDetalleId(dto.getPrescripcionDetalleId());
        entidad.setLoteId(dto.getLoteId());

        if (dto.getUsuarioId() != null) {
            Usuario usuario = usuarioRepository.findById(dto.getUsuarioId())
                    .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
            entidad.setUsuario(usuario);
        }

        if (dto.getAlmacenId() != null) {
            Almacen almacen = almacenRepository.findById(dto.getAlmacenId())
                    .orElseThrow(() -> new RuntimeException("Almacén no encontrado"));
            entidad.setAlmacen(almacen);
        }

        return convertirASalida(repository.save(entidad));
    }

    @Override
    @Transactional
    public Dispensa_Salida editar(Integer id, Dispensa_Actualizar dto) {
        Dispensa existente = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Dispensa no encontrada"));

        existente.setFecha(dto.getFecha());
        existente.setCantidad(dto.getCantidad());
        existente.setPrescripcionDetalleId(dto.getPrescripcionDetalleId());
        existente.setLoteId(dto.getLoteId());

        if (dto.getUsuarioId() != null) {
            Usuario usuario = usuarioRepository.findById(dto.getUsuarioId())
                    .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
            existente.setUsuario(usuario);
        }

        if (dto.getAlmacenId() != null) {
            Almacen almacen = almacenRepository.findById(dto.getAlmacenId())
                    .orElseThrow(() -> new RuntimeException("Almacén no encontrado"));
            existente.setAlmacen(almacen);
        }

        return convertirASalida(repository.save(existente));
    }

    @Override
    @Transactional
    public void eliminarPorId(Integer id) {
        repository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Dispensa_Salida> obtenerPorPrescripcion(Integer prescripcionDetalleId) {
        return repository.findByPrescripcionDetalleId(prescripcionDetalleId).stream()
                .map(this::convertirASalida)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<Dispensa_Salida> obtenerPorAlmacen(Integer almacenId) {
        return repository.findByAlmacenId(almacenId).stream()
                .map(this::convertirASalida)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<Dispensa_Salida> obtenerPorFecha(LocalDate fecha) {
        return repository.findByFecha(fecha).stream()
                .map(this::convertirASalida)
                .collect(Collectors.toList());
    }

    // Conversión manual de entidad a DTO
    private Dispensa_Salida convertirASalida(Dispensa entidad) {
        Dispensa_Salida salida = new Dispensa_Salida();
        salida.setId(entidad.getId());
        salida.setFecha(entidad.getFecha());
        salida.setCantidad(entidad.getCantidad());
        salida.setPrescripcionDetalleId(entidad.getPrescripcionDetalleId());
        salida.setLoteId(entidad.getLoteId());

        if (entidad.getUsuario() != null) {
            salida.setUsuarioNombre(entidad.getUsuario().getNombreCompleto());
        }

        if (entidad.getAlmacen() != null) {
            salida.setAlmacenNombre(entidad.getAlmacen().getNombre());
        }

        return salida;
    }
}
