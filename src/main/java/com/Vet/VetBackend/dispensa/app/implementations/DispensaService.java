package com.Vet.VetBackend.dispensa.app.implementations;

import com.Vet.VetBackend.dispensa.web.dto.Dispensa_Actualizar;
import com.Vet.VetBackend.dispensa.web.dto.Dispensa_Guardar;
import com.Vet.VetBackend.dispensa.web.dto.Dispensa_Salida;
import com.Vet.VetBackend.dispensa.domain.Dispensa;
import com.Vet.VetBackend.dispensa.repo.IDispensaRepository;
import com.Vet.VetBackend.dispensa.app.services.IDispensaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DispensaService implements IDispensaService {

    @Autowired
    private IDispensaRepository repository;

    @Override
    public List<Dispensa_Salida> obtenerTodas() {
        return repository.findAll().stream()
                .map(this::convertirASalida)
                .collect(Collectors.toList());
    }

    @Override
    public Page<Dispensa_Salida> obtenerTodasPaginadas(Pageable pageable) {
        Page<Dispensa> page = repository.findAll(pageable);
        List<Dispensa_Salida> dtoList = page.stream()
                .map(this::convertirASalida)
                .collect(Collectors.toList());
        return new PageImpl<>(dtoList, page.getPageable(), page.getTotalElements());
    }

    @Override
    public Dispensa_Salida obtenerPorId(Integer id) {
        Dispensa entidad = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Dispensa no encontrada"));
        return convertirASalida(entidad);
    }

    @Override
    public Dispensa_Salida crear(Dispensa_Guardar dto) {
        Dispensa entidad = new Dispensa();
        entidad.setFecha(dto.getFecha());
        entidad.setCantidad(dto.getCantidad());
        entidad.setPrescripcionDetalleId(dto.getPrescripcionDetalleId());
        entidad.setAlmacenId(dto.getAlmacenId());
        return convertirASalida(repository.save(entidad));
    }

    @Override
    public Dispensa_Salida editar(Integer id, Dispensa_Actualizar dto) {
        Dispensa existente = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Dispensa no encontrada"));
        existente.setFecha(dto.getFecha());
        existente.setCantidad(dto.getCantidad());
        existente.setPrescripcionDetalleId(dto.getPrescripcionDetalleId());
        existente.setAlmacenId(dto.getAlmacenId());
        return convertirASalida(repository.save(existente));
    }

    @Override
    public void eliminarPorId(Integer id) {
        repository.deleteById(id);
    }

    @Override
    public List<Dispensa_Salida> obtenerPorPrescripcion(Integer prescripcionDetalleId) {
        return repository.findByPrescripcionDetalleId(prescripcionDetalleId).stream()
                .map(this::convertirASalida)
                .collect(Collectors.toList());
    }

    @Override
    public List<Dispensa_Salida> obtenerPorAlmacen(Integer almacenId) {
        return repository.findByAlmacenId(almacenId).stream()
                .map(this::convertirASalida)
                .collect(Collectors.toList());
    }

    @Override
    public List<Dispensa_Salida> obtenerPorFecha(LocalDate fecha) {
        return repository.findByFecha(fecha).stream()
                .map(this::convertirASalida)
                .collect(Collectors.toList());
    }

    // 🔄 Conversión manual de entidad a DTO
    private Dispensa_Salida convertirASalida(Dispensa entidad) {
        Dispensa_Salida salida = new Dispensa_Salida();
        salida.setId(entidad.getId());
        salida.setFecha(entidad.getFecha());
        salida.setCantidad(entidad.getCantidad());
        salida.setPrescripcionDetalleId(entidad.getPrescripcionDetalleId());
        salida.setAlmacenId(entidad.getAlmacenId());
        return salida;
    }
}
