package com.Vet.VetBackend.compras.app.implementations;

import com.Vet.VetBackend.compras.app.services.CompraDetalleService;
import com.Vet.VetBackend.compras.domain.CompraDetalle;
import com.Vet.VetBackend.compras.repo.CompraDetalleRepository;
import com.Vet.VetBackend.compras.web.dto.ActualizarDetalle;
import com.Vet.VetBackend.compras.web.dto.CancelarDetalle;
import com.Vet.VetBackend.compras.web.dto.CrearDetalle;
import com.Vet.VetBackend.compras.web.dto.ObtenerDetalle;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CompraDetalleServiceImpl implements CompraDetalleService {

    @Autowired
    private CompraDetalleRepository repository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public List<ObtenerDetalle> compra_detalle() {
        return repository.findAll().stream()
                .map(detalle -> modelMapper.map(detalle, ObtenerDetalle.class))
                .collect(Collectors.toList());
    }

    @Override
    public ObtenerDetalle detalle_por_id(Long id) {
        CompraDetalle detalle = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Detalle de compra no encontrado"));
        return modelMapper.map(detalle, ObtenerDetalle.class);
    }

    @Override
    public ObtenerDetalle agregar_detalle(CrearDetalle dto) {
        CompraDetalle detalle = new CompraDetalle();
        detalle.setCompraId(dto.getCompraId()); // ✅ aquí asignas correctamente
        detalle.setCantidad(dto.getCantidad());
        detalle.setPrecio(dto.getPrecio());

        CompraDetalle saved = repository.save(detalle);
        return mapToDto(saved); // mapeo manual a DTO
    }

    private ObtenerDetalle mapToDto(CompraDetalle detalle) {
        ObtenerDetalle dto = new ObtenerDetalle();
        dto.setId(detalle.getId());
        dto.setCompraId(detalle.getCompraId());
        dto.setCantidad(detalle.getCantidad());
        dto.setPrecio(detalle.getPrecio());
        return dto;
    }


    @Override
    public ObtenerDetalle actualizar_detalle(Long id, ActualizarDetalle dto) {
        CompraDetalle existente = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Detalle de compra no encontrado"));
        existente.setCantidad(dto.getCantidad());
        existente.setPrecio(dto.getPrecio());
        return modelMapper.map(repository.save(existente), ObtenerDetalle.class);
    }

    @Override
    public void eliminar_detalle(Long id, CancelarDetalle dto) {
        CompraDetalle detalle = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Detalle de compra no encontrado"));
        repository.delete(detalle); // 🔧 Temporal: se actualizará cuando se agregue campo estado
    }

    @Override
    public List<ObtenerDetalle> detalles_por_compra(Long compraId) {
        return repository.findByCompraId(compraId).stream()
                .map(detalle -> modelMapper.map(detalle, ObtenerDetalle.class))
                .collect(Collectors.toList());
    }
}
