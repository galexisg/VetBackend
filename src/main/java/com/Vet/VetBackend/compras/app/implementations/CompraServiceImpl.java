package com.Vet.VetBackend.compras.app.implementations;

import com.Vet.VetBackend.compras.app.services.CompraService;
import com.Vet.VetBackend.compras.domain.Compra;
import com.Vet.VetBackend.compras.repo.CompraRepository;
import com.Vet.VetBackend.compras.web.dto.CompraActualizar;
import com.Vet.VetBackend.compras.web.dto.CompraCancelar;
import com.Vet.VetBackend.compras.web.dto.CompraCrear;
import com.Vet.VetBackend.compras.web.dto.CompraObtener;
import com.Vet.VetBackend.proveedores.domain.Proveedor;
import com.Vet.VetBackend.proveedores.repo.IProveedorRepository;
import com.Vet.VetBackend.usuarios.domain.Usuario;
import com.Vet.VetBackend.usuarios.repo.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CompraServiceImpl implements CompraService {

    @Autowired
    private CompraRepository repository;

    @Autowired
    private IProveedorRepository proveedorRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public List<CompraObtener> obtenerTodos() {
        return repository.findAll()
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public CompraObtener obtenerPorId(Long id) {
        Compra compra = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Compra no encontrada"));
        return toDto(compra);
    }

    @Override
    public CompraObtener crear(CompraCrear dto) {
        Compra compra = new Compra();
        compra.setFecha(dto.getFecha());
        compra.setDescripcion(dto.getDescripcion());
        compra.setTotal(dto.getTotal());

        Proveedor proveedor = proveedorRepository
                .findById(dto.getProveedorId())
                .orElseThrow(() -> new RuntimeException("Proveedor no encontrado"));
        compra.setProveedor(proveedor);

        Usuario usuario = usuarioRepository
                .findById(dto.getUsuarioId())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        compra.setUsuario(usuario);

        return toDto(repository.save(compra));
    }

    @Override
    public CompraObtener actualizar(Long id, CompraActualizar dto) {
        Compra existente = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Compra no encontrada"));

        existente.setFecha(dto.getFecha());
        existente.setDescripcion(dto.getDescripcion());
        existente.setTotal(dto.getTotal());

        Proveedor proveedor = proveedorRepository
                .findById(dto.getProveedorId())
                .orElseThrow(() -> new RuntimeException("Proveedor no encontrado"));
        existente.setProveedor(proveedor);

        Usuario usuario = usuarioRepository
                .findById(dto.getUsuarioId())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        existente.setUsuario(usuario);

        return toDto(repository.save(existente));
    }

    @Override
    public void cancelar(Long id, CompraCancelar dto) {
        repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Compra no encontrada"));
        repository.deleteById(id);
    }

    @Override
    public List<CompraObtener> obtenerPorProveedor(Integer proveedorId) {
        return repository.findByProveedor_Id(proveedorId)
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<CompraObtener> obtenerPorFecha(LocalDate fecha) {
        return repository.findByFecha(fecha)
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<CompraObtener> obtenerPorUsuario(Integer usuarioId) {
        return repository.findByUsuario_Id(usuarioId)
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    private CompraObtener toDto(Compra compra) {
        CompraObtener dto = new CompraObtener();
        dto.setId(compra.getId().intValue());
        dto.setFecha(compra.getFecha());
        dto.setDescripcion(compra.getDescripcion());
        dto.setTotal(compra.getTotal());
        dto.setProveedorId(compra.getProveedor().getId());
        dto.setUsuarioId(compra.getUsuario().getId());
        return dto;
    }
}
