package com.Vet.VetBackend.Medicamento.app.implementations;

import com.Vet.VetBackend.Medicamento.web.dto.MedicamentoGuardar;
import com.Vet.VetBackend.Medicamento.web.dto.MedicamentoModificar;
import com.Vet.VetBackend.Medicamento.web.dto.MedicamentoSalida;
import com.Vet.VetBackend.Medicamento.domain.Medicamento;
import com.Vet.VetBackend.Medicamento.repo.IMedicamentoRepository;
import com.Vet.VetBackend.Medicamento.app.services.IMedicamentoService;

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
public class MedicamentoService implements IMedicamentoService {

    private static final Logger log = LoggerFactory.getLogger(MedicamentoService.class);

    @Autowired
    private IMedicamentoRepository medicamentoRepository;

    @Override
    public List<MedicamentoSalida> obtenerTodos() {
        List<Medicamento> medicamentos = medicamentoRepository.findAll();
        return medicamentos.stream()
                .map(MedicamentoSalida::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public Page<MedicamentoSalida> obtenerTodosPaginados(Pageable pageable) {
        Page<Medicamento> page = medicamentoRepository.findAll(pageable);

        List<MedicamentoSalida> medicamentoDto = page.stream()
                .map(MedicamentoSalida::fromEntity)
                .collect(Collectors.toList());

        return new PageImpl<>(medicamentoDto, page.getPageable(), page.getTotalElements());
    }

    @Override
    public MedicamentoSalida obtenerPorId(Integer id) {
        Medicamento medicamento = medicamentoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Medicamento no encontrado"));
        return MedicamentoSalida.fromEntity(medicamento);
    }

    @Override
    public MedicamentoSalida crear(MedicamentoGuardar dto) {
        Medicamento medicamento = toEntity(dto);
        medicamento = medicamentoRepository.save(medicamento);
        return MedicamentoSalida.fromEntity(medicamento);
    }

    @Override
    public MedicamentoSalida editar(MedicamentoModificar dto) {
        Medicamento medicamento = toEntity(dto);
        medicamento = medicamentoRepository.save(medicamento);
        return MedicamentoSalida.fromEntity(medicamento);
    }

    @Override
    public void eliminarPorId(Integer id) {
        medicamentoRepository.deleteById(id);
    }

    // --- Métodos auxiliares para convertir DTOs a Entity ---

    private Medicamento toEntity(MedicamentoGuardar dto) {
        Medicamento m = new Medicamento();
        m.setNombre(dto.getNombre());
        m.setFormafarmacautica(dto.getFormafarmacautica());
        m.setConcentracion(dto.getConcentracion());
        m.setUnidad(dto.getUnidad());
        m.setVia(dto.getVia());
        m.setRequiereReceta(dto.getRequiereReceta());
        m.setActivo(dto.isActivo());
        m.setTemperaturaalm(dto.getTemperaturaalm());
        m.setVidautilmeses(dto.getVidautilmeses());
        return m;
    }

    private Medicamento toEntity(MedicamentoModificar dto) {
        Medicamento m = new Medicamento();
        m.setId(dto.getId());
        m.setNombre(dto.getNombre());
        m.setFormafarmacautica(dto.getFormafarmacautica());
        m.setConcentracion(dto.getConcentracion());
        m.setUnidad(dto.getUnidad());
        m.setVia(dto.getVia());
        m.setRequiereReceta(dto.getRequiereReceta());
        m.setActivo(dto.getActivo());
        m.setTemperaturaalm(dto.getTemperaturaalm());
        m.setVidautilmeses(dto.getVidautilmeses());
        return m;
    }
}
