package com.Vet.VetBackend.proveedores.app.implementations;

import com.Vet.VetBackend.proveedores.web.dto.ProveedorGuardar;
import com.Vet.VetBackend.proveedores.web.dto.ProveedorModificar;
import com.Vet.VetBackend.proveedores.web.dto.ProveedorSalida;
import com.Vet.VetBackend.proveedores.domain.Proveedor;
import com.Vet.VetBackend.proveedores.repo.IProveedorRepository;
import com.Vet.VetBackend.proveedores.app.services.IProveedorService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProveedorService implements IProveedorService {

    private static final Logger log = LoggerFactory.getLogger(ProveedorService.class);

    @Autowired
    private IProveedorRepository proveedorRepository;

    @Override
    public List<ProveedorSalida> obtenerTodos() {
        List<Proveedor> proveedores = proveedorRepository.findAll();
        return proveedores.stream()
                .map(ProveedorSalida::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public Page<ProveedorSalida> obtenerTodosPaginados(Pageable pageable) {
        Page<Proveedor> page = proveedorRepository.findAll(pageable);

        List<ProveedorSalida> proveedorDto = page.stream()
                .map(ProveedorSalida::fromEntity)
                .collect(Collectors.toList());

        return new PageImpl<>(proveedorDto, page.getPageable(), page.getTotalElements());
    }

    @Override
    public ProveedorSalida obtenerPorId(Integer id) {
        Proveedor proveedor = proveedorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Proveedor no encontrado"));
        return ProveedorSalida.fromEntity(proveedor);
    }

    @Override
    public ProveedorSalida crear(ProveedorGuardar proveedorGuardar) {
        Proveedor proveedor = toEntity(proveedorGuardar);
        proveedor = proveedorRepository.save(proveedor);
        return ProveedorSalida.fromEntity(proveedor);
    }

    @Override
    public ProveedorSalida editar(ProveedorModificar proveedorModificar) {
        Proveedor proveedor = toEntity(proveedorModificar);
        proveedor = proveedorRepository.save(proveedor);
        return ProveedorSalida.fromEntity(proveedor);
    }

    @Override
    public void eliminarPorId(Integer id) {
        proveedorRepository.deleteById(id);
    }

    // --- Métodos auxiliares para convertir DTOs a Entity ---
    private Proveedor toEntity(ProveedorGuardar dto) {
        Proveedor p = new Proveedor();
        p.setNombre(dto.getNombre());
        p.setNit(dto.getNit());
        p.setTelefono(dto.getTelefono());
        p.setEmail(dto.getEmail());
        p.setDireccion(dto.getDireccion());
        p.setEstadoid(dto.getEstadoid());
        p.setNotas(dto.getNotas());
        return p;
    }

    private Proveedor toEntity(ProveedorModificar dto) {
        Proveedor p = new Proveedor();
        p.setId(dto.getId()); // importante para editar
        p.setNombre(dto.getNombre());
        p.setNit(dto.getNit());
        p.setTelefono(dto.getTelefono());
        p.setEmail(dto.getEmail());
        p.setDireccion(dto.getDireccion());
        p.setEstadoid(dto.getEstadoid());
        p.setNotas(dto.getNotas());
        return p;
    }
}
