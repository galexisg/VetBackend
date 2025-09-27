package com.Vet.VetBackend.proveedores.app.implementations;

import com.Vet.VetBackend.proveedores.web.dto.*;
import com.Vet.VetBackend.proveedores.domain.Proveedor;
import com.Vet.VetBackend.proveedores.repo.IProveedorRepository;
import com.Vet.VetBackend.proveedores.app.services.IProveedorService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
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
        return proveedorRepository.findAll().stream()
                .map(ProveedorSalida::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public Page<ProveedorSalida> obtenerTodosPaginados(Pageable pageable) {
        Page<Proveedor> page = proveedorRepository.findAll(pageable);
        List<ProveedorSalida> dtos = page.stream()
                .map(ProveedorSalida::fromEntity)
                .collect(Collectors.toList());
        return new PageImpl<>(dtos, page.getPageable(), page.getTotalElements());
    }

    @Override
    public ProveedorSalida obtenerPorId(Integer id) {
        Proveedor proveedor = proveedorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Proveedor no encontrado"));
        return ProveedorSalida.fromEntity(proveedor);
    }

    @Override
    public ProveedorSalida crear(ProveedorGuardar dto) {
        Proveedor proveedor = dto.toEntity();
        proveedor = proveedorRepository.save(proveedor);
        return ProveedorSalida.fromEntity(proveedor);
    }

    @Override
    public ProveedorSalida editar(ProveedorModificar dto) {
        Proveedor proveedor = dto.toEntity();
        proveedor = proveedorRepository.save(proveedor);
        return ProveedorSalida.fromEntity(proveedor);
    }

    @Override
    public void eliminarPorId(Integer id) {
        proveedorRepository.deleteById(id);
    }
}
