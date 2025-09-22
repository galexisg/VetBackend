package com.Vet.VetBackend.diagnostico.app.implementations;

import com.Vet.VetBackend.citas.domain.Cita;
import com.Vet.VetBackend.citas.repo.CitaRepository;
import com.Vet.VetBackend.diagnostico.app.services.DiagnosticoService;
import com.Vet.VetBackend.diagnostico.domain.Diagnostico;
import com.Vet.VetBackend.diagnostico.repo.DiagnosticoRepository;
import com.Vet.VetBackend.diagnostico.web.dto.DiagnosticoDto;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DiagnosticoServiceImpl implements DiagnosticoService {
    private final DiagnosticoRepository diagnosticoRepository;
    private final CitaRepository citaRepository;
    private final ModelMapper modelMapper;

    public DiagnosticoServiceImpl(DiagnosticoRepository diagnosticoRepository,
                                  CitaRepository citaRepository,
                                  ModelMapper modelMapper) {
        this.diagnosticoRepository = diagnosticoRepository;
        this.citaRepository = citaRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<DiagnosticoDto> listarPorCita(Long citaId) {
        return diagnosticoRepository.findByCita_CitaId(citaId).stream()
                .map(d -> modelMapper.map(d, DiagnosticoDto.class))
                .toList();
    }

    @Override
    public DiagnosticoDto crear(Long citaId, DiagnosticoDto dto) {
        Cita cita = citaRepository.findById(citaId)
                .orElseThrow(() -> new IllegalArgumentException("Cita no encontrada"));

        Diagnostico diagnostico = new Diagnostico();
        diagnostico.setCita(cita);
        diagnostico.setNombre(dto.getNombre());
        diagnostico.setDescripcion(dto.getDescripcion());
        diagnostico.setActivo(true);

        return modelMapper.map(diagnosticoRepository.save(diagnostico), DiagnosticoDto.class);
    }

    @Override
    public DiagnosticoDto editar(Long id, DiagnosticoDto dto) {
        Diagnostico diagnostico = diagnosticoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Diagnóstico no encontrado"));

        if (dto.getNombre() != null) {
            diagnostico.setNombre(dto.getNombre());
        }
        if (dto.getDescripcion() != null) {
            diagnostico.setDescripcion(dto.getDescripcion());
        }

        return modelMapper.map(diagnosticoRepository.save(diagnostico), DiagnosticoDto.class);
    }

    @Override
    public DiagnosticoDto cambiarEstado(Long id, boolean activo) {
        Diagnostico diagnostico = diagnosticoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Diagnóstico no encontrado"));

        diagnostico.setActivo(activo);
        return modelMapper.map(diagnosticoRepository.save(diagnostico), DiagnosticoDto.class);
    }
}

