package com.Vet.VetBackend.servicios.app.implementations;

import com.Vet.VetBackend.servicios.app.services.ServicioService;
import com.Vet.VetBackend.servicios.domain.EstadoServicio;
import com.Vet.VetBackend.servicios.domain.Servicio;
import com.Vet.VetBackend.servicios.repo.MotivoServicioRepository;
import com.Vet.VetBackend.servicios.repo.ServicioRepository;
import com.Vet.VetBackend.servicios.web.dto.MotivoRes;
import com.Vet.VetBackend.servicios.web.dto.ServicioReq;
import com.Vet.VetBackend.servicios.web.dto.ServicioRes;
import com.Vet.VetBackend.servicios.web.dto.ServicioEstadoReq;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@Transactional
public class ServicioServiceImpl implements ServicioService {

    private final ServicioRepository repo;
    private final MotivoServicioRepository msRepo;

    public ServicioServiceImpl(ServicioRepository repo, MotivoServicioRepository msRepo) {
        this.repo = repo;
        this.msRepo = msRepo;
    }

    @Override
    public ServicioRes crear(ServicioReq req) {
        validar(req);
        repo.findByNombreIgnoreCase(req.getNombre().trim())
                .ifPresent(s -> {
                    throw new IllegalArgumentException("nombre ya existe");
                });

        Servicio s = aplicar(req, new Servicio());
        return map(repo.save(s));
    }

    @Override
    public ServicioRes actualizar(Long id, ServicioReq req) {
        validar(req);
        Servicio s = repo.findById(id).orElseThrow(() -> new NoSuchElementException("no encontrado"));

        String nuevoNombre = req.getNombre().trim();
        if (!Objects.equals(s.getNombre().toLowerCase(), nuevoNombre.toLowerCase())
                && repo.findByNombreIgnoreCase(nuevoNombre).isPresent()) {
            throw new IllegalArgumentException("nombre ya existe");
        }

        s = aplicar(req, s);
        return map(repo.save(s));
    }

    @Override
    @Transactional(readOnly = true)
    public ServicioRes obtener(Long id) {
        return map(repo.findById(id).orElseThrow(() -> new NoSuchElementException("no encontrado")));
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ServicioRes> listar(String q, Boolean activo, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("nombre").ascending());

        Specification<Servicio> spec = (root, query, cb) -> cb.conjunction();

        if (q != null && !q.isBlank()) {
            String like = "%" + q.toLowerCase().trim() + "%";
            spec = spec.and((root, query, cb) -> cb.like(cb.lower(root.get("nombre")), like));
        }

        if (activo != null) {
            EstadoServicio estado = activo ? EstadoServicio.ACTIVO : EstadoServicio.INACTIVO;
            spec = spec.and((root, query, cb) -> cb.equal(root.get("estado"), estado));
        }

        return repo.findAll(spec, pageable).map(this::map);
    }

    @Override
    public ServicioRes cambiarEstado(Long id, ServicioEstadoReq req) {
        Servicio s = repo.findById(id).orElseThrow(() -> new NoSuchElementException("no encontrado"));
        s.setEstado(req.getEstado());
        return map(repo.save(s));
    }

    // ✅ Nuevo método: listar motivos vinculados a un servicio
    @Override
    @Transactional(readOnly = true)
    public List<MotivoRes> listarMotivosPorServicio(Long servicioId) {
        // Verificar que el servicio exista
        if (!repo.existsById(servicioId)) {
            throw new NoSuchElementException("servicio no encontrado");
        }

        // Consultar motivos asociados y mapearlos a DTO
        return msRepo.findMotivosByServicioId(servicioId).stream()
                .map(m -> MotivoRes.builder()
                        .id(m.getId())
                        .nombre(m.getNombre())
                        .build())
                .collect(Collectors.toList());
    }

    // ✅ Validación de datos antes de guardar
    private void validar(ServicioReq r) {
        if (r.getNombre() == null || r.getNombre().isBlank())
            throw new IllegalArgumentException("nombre requerido");
        if (r.getPrecioBase() != null && r.getPrecioBase().compareTo(BigDecimal.ZERO) < 0)
            throw new IllegalArgumentException("precio_base >= 0");
    }

    // ✅ Aplica los cambios del DTO a la entidad
    private Servicio aplicar(ServicioReq r, Servicio s) {
        s.setNombre(r.getNombre().trim());
        s.setDescripcion(r.getDescripcion());
        s.setPrecioBase(r.getPrecioBase());
        if (r.getEstado() != null) s.setEstado(r.getEstado());
        return s;
    }

    // ✅ Mapea entidad a DTO de respuesta
    private ServicioRes map(Servicio s) {
        return ServicioRes.builder()
                .id(s.getId())
                .nombre(s.getNombre())
                .descripcion(s.getDescripcion())
                .precioBase(s.getPrecioBase())
                .estado(s.getEstado())
                .createdAt(s.getCreatedAt())
                .updatedAt(s.getUpdatedAt())
                .build();
    }
}
