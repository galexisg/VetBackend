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

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public List<Dispensa_Salida> obtenerTodas() {
        return repository.findAll().stream()
                .map(d -> modelMapper.map(d, Dispensa_Salida.class))
                .collect(Collectors.toList());
    }

    @Override
    public Page<Dispensa_Salida> obtenerTodasPaginadas(Pageable pageable) {
        Page<Dispensa> page = repository.findAll(pageable);
        List<Dispensa_Salida> dtoList = page.stream()
                .map(d -> modelMapper.map(d, Dispensa_Salida.class))
                .collect(Collectors.toList());
        return new PageImpl<>(dtoList, page.getPageable(), page.getTotalElements());
    }

    @Override
    public Dispensa_Salida obtenerPorId(Integer id) {
        Dispensa entidad = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Dispensa no encontrada"));
        return modelMapper.map(entidad, Dispensa_Salida.class);
    }

    @Override
    public Dispensa_Salida crear(Dispensa_Guardar dto) {
        Dispensa entidad = modelMapper.map(dto, Dispensa.class);
        return modelMapper.map(repository.save(entidad), Dispensa_Salida.class);
    }

    @Override
    public Dispensa_Salida editar(Integer id, Dispensa_Actualizar dto) {
        Dispensa existente = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Dispensa no encontrada"));
        modelMapper.map(dto, existente);
        return modelMapper.map(repository.save(existente), Dispensa_Salida.class);
    }

    @Override
    public void eliminarPorId(Integer id) {
        repository.deleteById(id);
    }

    @Override
    public List<Dispensa_Salida> obtenerPorPrescripcion(Integer prescripcionDetalleId) {
        return repository.findByPrescripcionDetalleId(prescripcionDetalleId).stream()
                .map(d -> modelMapper.map(d, Dispensa_Salida.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<Dispensa_Salida> obtenerPorAlmacen(Integer almacenId) {
        return repository.findByAlmacenId(almacenId).stream()
                .map(d -> modelMapper.map(d, Dispensa_Salida.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<Dispensa_Salida> obtenerPorFecha(LocalDate fecha) {
        return repository.findByFecha(fecha).stream()
                .map(d -> modelMapper.map(d, Dispensa_Salida.class))
                .collect(Collectors.toList());
    }
}
