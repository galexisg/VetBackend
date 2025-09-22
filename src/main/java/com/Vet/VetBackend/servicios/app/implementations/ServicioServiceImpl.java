// src/main/java/com/Vet/VetBackend/servicios/app/implementations/ServicioServiceImpl.java
package com.Vet.VetBackend.servicios.app.implementations;

import com.Vet.VetBackend.servicios.app.services.ServicioService;
import com.Vet.VetBackend.servicios.domain.Servicio;
import com.Vet.VetBackend.servicios.domain.Servicio.EstadoServicio;
import com.Vet.VetBackend.servicios.repo.ServicioRepository;
import com.Vet.VetBackend.servicios.web.dto.ServicioReq;
import com.Vet.VetBackend.servicios.web.dto.ServicioRes;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.NoSuchElementException;
import java.util.Objects;

@Service
@Transactional
public class ServicioServiceImpl implements ServicioService {

    private final ServicioRepository repo;

    public ServicioServiceImpl(ServicioRepository repo) { this.repo = repo; }

    @Override
    public ServicioRes crear(ServicioReq req) {
        validar(req);
        repo.findByNombreIgnoreCase(req.getNombre().trim())
                .ifPresent(s -> { throw new IllegalArgumentException("nombre ya existe"); });

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
    public ServicioRes activar(Long id, boolean activo) {
        Servicio s = repo.findById(id).orElseThrow(() -> new NoSuchElementException("no encontrado"));
        s.setEstado(activo ? EstadoServicio.ACTIVO : EstadoServicio.INACTIVO);
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

    private void validar(ServicioReq r) {
        if (r.getNombre() == null || r.getNombre().isBlank())
            throw new IllegalArgumentException("nombre requerido");
        if (r.getPrecioBase() != null && r.getPrecioBase().compareTo(BigDecimal.ZERO) < 0)
            throw new IllegalArgumentException("precio_base >= 0");
    }

    private Servicio aplicar(ServicioReq r, Servicio s) {
        s.setNombre(r.getNombre().trim());
        s.setDescripcion(r.getDescripcion());
        s.setPrecioBase(r.getPrecioBase());
        if (r.getActivo() != null) {
            s.setEstado(r.getActivo() ? EstadoServicio.ACTIVO : EstadoServicio.INACTIVO);
        }
        return s;
    }

    private ServicioRes map(Servicio s) {
        return ServicioRes.builder()
                .id(s.getId())
                .nombre(s.getNombre())
                .descripcion(s.getDescripcion())
                .precioBase(s.getPrecioBase())
                // Exponemos boolean en el response para compatibilidad
                .activo(s.getEstado() == EstadoServicio.ACTIVO)
                .createdAt(s.getCreatedAt())
                .updatedAt(s.getUpdatedAt())
                .build();
    }
}
