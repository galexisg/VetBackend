package com.Vet.VetBackend.citas.app.implementations;

import com.Vet.VetBackend.citas.domain.Cita;
import com.Vet.VetBackend.citas.repo.CitaRepository;
import com.Vet.VetBackend.citas.app.services.CitaService;
import com.Vet.VetBackend.citas.web.dto.CitaRequestDTO;
import com.Vet.VetBackend.citas.web.dto.CitaResponseDTO;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
@Transactional
public class CitaServiceImpl implements CitaService {

    private final CitaRepository citaRepository;

    public CitaServiceImpl(CitaRepository citaRepository) {
        this.citaRepository = citaRepository;
    }

    @Override
    public CitaResponseDTO crear(CitaRequestDTO dto) {
        Cita cita = mapToEntity(dto);
        Cita saved = citaRepository.save(cita);
        return mapToResponse(saved);
    }

    @Override
    public CitaResponseDTO actualizar(Long id, CitaRequestDTO dto) {
        Cita existing = citaRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Cita no encontrada con id: " + id));

        existing.setMascotaId(dto.getMascotaId());
        existing.setUsuarioId(dto.getUsuarioId());
        existing.setVeterinarioId(dto.getVeterinarioId());
        existing.setMotivoId(dto.getMotivoId());
        existing.setCitaEstadoId(dto.getCitaEstadoId());
        existing.setFacturaId(dto.getFacturaId());
        existing.setConsultaId(dto.getConsultaId());
        existing.setTipo(dto.getTipo() == null ? existing.getTipo() : dto.getTipo());
        existing.setFechaHora(dto.getFechaHora());
        existing.setObservaciones(dto.getObservaciones());

        Cita updated = citaRepository.save(existing);
        return mapToResponse(updated);
    }

    @Override
    @Transactional(readOnly = true)
    public CitaResponseDTO obtenerPorId(Long id) {
        Cita c = citaRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Cita no encontrada con id: " + id));
        return mapToResponse(c);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CitaResponseDTO> listarTodos() {
        return citaRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    private Cita mapToEntity(CitaRequestDTO dto) {
        Cita c = new Cita();
        c.setMascotaId(dto.getMascotaId());
        c.setUsuarioId(dto.getUsuarioId());
        c.setVeterinarioId(dto.getVeterinarioId());
        c.setMotivoId(dto.getMotivoId());
        c.setCitaEstadoId(dto.getCitaEstadoId());
        c.setFacturaId(dto.getFacturaId());
        c.setConsultaId(dto.getConsultaId());
        c.setTipo(dto.getTipo() == null ? Cita.Tipo.Normal : dto.getTipo());
        c.setFechaHora(dto.getFechaHora());
        c.setObservaciones(dto.getObservaciones());
        return c;
    }

    private CitaResponseDTO mapToResponse(Cita c) {
        CitaResponseDTO r = new CitaResponseDTO();
        r.setCitaId(c.getCitaId());
        r.setMascotaId(c.getMascotaId());
        r.setUsuarioId(c.getUsuarioId());
        r.setVeterinarioId(c.getVeterinarioId());
        r.setMotivoId(c.getMotivoId());
        r.setCitaEstadoId(c.getCitaEstadoId());
        r.setFacturaId(c.getFacturaId());
        r.setConsultaId(c.getConsultaId());
        r.setTipo(c.getTipo());
        r.setFechaHora(c.getFechaHora());
        r.setObservaciones(c.getObservaciones());
        return r;
    }
}