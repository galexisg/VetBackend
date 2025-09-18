package com.Vet.VetBackend.compras.app.implementations;

import com.Vet.VetBackend.compras.app.services.CompraService;
import com.Vet.VetBackend.compras.domain.Compra;
import com.Vet.VetBackend.compras.repo.CompraRepository;
import com.Vet.VetBackend.compras.web.dto.CompraActualizar;
import com.Vet.VetBackend.compras.web.dto.CompraCancelar;
import com.Vet.VetBackend.compras.web.dto.CompraCrear;
import com.Vet.VetBackend.compras.web.dto.CompraObtener;
import org.modelmapper.ModelMapper;
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
    private ModelMapper modelMapper;

    @Override
    public List<CompraObtener> obtenerTodos() {
        return repository.findAll().stream()
                .map(compra -> modelMapper.map(compra, CompraObtener.class))
                .collect(Collectors.toList());
    }

    @Override
    public CompraObtener obtenerPorId(Integer id) {
        Compra compra = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Compra no encontrada"));
        return modelMapper.map(compra, CompraObtener.class);
    }

    @Override
    public CompraObtener crear(CompraCrear dto) {
        Compra compra = modelMapper.map(dto, Compra.class);
        return modelMapper.map(repository.save(compra), CompraObtener.class);
    }

    @Override
    public CompraObtener actualizar(Integer id, CompraActualizar dto) {
        Compra existente = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Compra no encontrada"));
        modelMapper.map(dto, existente);
        return modelMapper.map(repository.save(existente), CompraObtener.class);
    }

    @Override
    public void cancelar(Integer id, CompraCancelar dto) {
        // En este método, podrías usar el 'motivoCancelacion' del DTO
        Compra compra = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Compra no encontrada"));
        // Aquí puedes actualizar el estado de la compra a 'CANCELADA' en lugar de eliminarla
        repository.deleteById(id);
    }

    @Override
    public List<CompraObtener> obtenerPorProveedor(Integer proveedorId) {
        return repository.findByProveedorId(proveedorId).stream()
                .map(compra -> modelMapper.map(compra, CompraObtener.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<CompraObtener> obtenerPorFecha(LocalDate fecha) {
        return repository.findByFecha(fecha).stream()
                .map(compra -> modelMapper.map(compra, CompraObtener.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<CompraObtener> obtenerPorUsuario(Integer usuarioId) {
        return repository.findByUsuarioId(usuarioId).stream()
                .map(compra -> modelMapper.map(compra, CompraObtener.class))
                .collect(Collectors.toList());
    }
}