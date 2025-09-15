// src/main/java/com/Vet/VetBackend/servicios/app/ServicioServiceImpl.java
package com.Vet.VetBackend.servicios.app.implementations;

import com.Vet.VetBackend.servicios.app.services.ServicioService;
import com.Vet.VetBackend.servicios.domain.Servicio;
import com.Vet.VetBackend.servicios.repo.ServicioRepository;
import com.Vet.VetBackend.servicios.web.dto.ServicioReq;
import com.Vet.VetBackend.servicios.web.dto.ServicioRes;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.NoSuchElementException;

@Service
@Transactional
public class ServicioServiceImpl implements ServicioService {

    private final ServicioRepository repo;

    public ServicioServiceImpl(ServicioRepository repo) { this.repo = repo; }

    @Override
    public ServicioRes crear(ServicioReq req) {
        validar(req);
        repo.findByNombreIgnoreCase(req.getNombre())
                .ifPresent(s -> { throw new IllegalArgumentException("nombre ya existe"); });
        Servicio s = aplicar(req, new Servicio());
        return map(repo.save(s));
    }

    @Override
    public ServicioRes actualizar(Long id, ServicioReq req) {
        validar(req);
        Servicio s = repo.findById(id).orElseThrow(() -> new NoSuchElementException("no encontrado"));
        if (!s.getNombre().equalsIgnoreCase(req.getNombre())
                && repo.findByNombreIgnoreCase(req.getNombre()).isPresent()) {
            throw new IllegalArgumentException("nombre ya existe");
        }
        s = aplicar(req, s);
        return map(repo.save(s));
    }

    @Override
    public ServicioRes activar(Long id, boolean activo) {
        Servicio s = repo.findById(id).orElseThrow(() -> new NoSuchElementException("no encontrado"));
        s.setActivo(activo);
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
        Specification<Servicio> spec = Specification.where(null);
        if (q != null && !q.isBlank()) {
            spec = spec.and((r, cb, cx) -> cb.like(cb.lower(r.get("nombre")), "%" + q.toLowerCase() + "%"));
        }
        if (activo != null) {
            spec = spec.and((r, cb, cx) -> cb.equal(r.get("activo"), activo));
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
        if (r.getActivo() != null) s.setActivo(r.getActivo());
        return s;
    }

    private ServicioRes map(Servicio s) {
        return ServicioRes.builder()
                .id(s.getId()).nombre(s.getNombre()).descripcion(s.getDescripcion())
                .precioBase(s.getPrecioBase()).activo(s.getActivo())
                .createdAt(s.getCreatedAt()).updatedAt(s.getUpdatedAt())
                .build();
    }
}
