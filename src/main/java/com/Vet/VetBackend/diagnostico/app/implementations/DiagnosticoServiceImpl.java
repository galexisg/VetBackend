package com.Vet.VetBackend.diagnostico.app.implementations;

import com.Vet.VetBackend.citas.domain.Cita;
import com.Vet.VetBackend.citas.repo.CitaRepository;
import com.Vet.VetBackend.diagnostico.app.services.DiagnosticoService;
import com.Vet.VetBackend.diagnostico.domain.Diagnostico;
import com.Vet.VetBackend.diagnostico.repo.DiagnosticoRepository;
import com.Vet.VetBackend.diagnostico.web.dto.DiagnosticoDto;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
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

        // DTO → Entidad (crear map vacío para evitar conflictos)
        modelMapper.emptyTypeMap(DiagnosticoDto.class, Diagnostico.class)
                .addMappings(m -> m.skip(Diagnostico::setCita)) // evitamos map automático
                .setPostConverter(ctx -> {
                    DiagnosticoDto src = ctx.getSource();
                    Diagnostico dest = ctx.getDestination();
                    if (src.getCitaId() != null) {
                        dest.setCita(
                                citaRepository.findById(src.getCitaId())
                                        .orElseThrow(() -> new IllegalArgumentException("Cita no encontrada"))
                        );
                    }
                    return dest;
                });

        // Entidad → DTO
        modelMapper.emptyTypeMap(Diagnostico.class, DiagnosticoDto.class)
                .addMapping(d -> d.getCita().getCitaId(), DiagnosticoDto::setCitaId);
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
        diagnostico.setEstadoDiagnostico(true); // siempre activo al crear
        diagnostico.setCreadoAt(LocalDateTime.now());

        Diagnostico guardado = diagnosticoRepository.save(diagnostico);
        return modelMapper.map(guardado, DiagnosticoDto.class);
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
    public DiagnosticoDto cambiarEstado(Long id, boolean estadoDiagnostico) {
        Diagnostico diagnostico = diagnosticoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Diagnóstico no encontrado"));

        diagnostico.setEstadoDiagnostico(estadoDiagnostico);

        Diagnostico actualizado = diagnosticoRepository.save(diagnostico);
        return modelMapper.map(actualizado, DiagnosticoDto.class);
    }
}

