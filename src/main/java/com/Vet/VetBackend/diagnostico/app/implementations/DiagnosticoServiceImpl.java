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
        List<Diagnostico> diagnosticos = diagnosticoRepository.findByCita_CitaId(citaId);

        return diagnosticos.stream().map(d -> {
            DiagnosticoDto dto = new DiagnosticoDto();
            dto.setId(d.getId());
            dto.setCitaId(d.getCita().getCitaId());
            dto.setNombre(d.getNombre());
            dto.setDescripcion(d.getDescripcion());
            dto.setEstadoDiagnostico(d.isEstadoDiagnostico());
            dto.setCreadoAt(d.getCreadoAt());
            return dto;
        }).toList();
    }

    @Override
    public DiagnosticoDto crear(Long citaId, DiagnosticoDto dto) {
        // 1. Buscar cita existente
        Cita cita = citaRepository.findById(citaId)
                .orElseThrow(() -> new IllegalArgumentException("Cita no encontrada"));

        // 2. Construir entidad Diagnostico
        Diagnostico diagnostico = new Diagnostico();
        diagnostico.setCita(cita);
        diagnostico.setNombre(dto.getNombre());
        diagnostico.setDescripcion(dto.getDescripcion());
        diagnostico.setEstadoDiagnostico(true); // siempre activo al crear

        // 3. Guardar en BD
        Diagnostico guardado = diagnosticoRepository.save(diagnostico);

        // 4. Armar respuesta DTO
        DiagnosticoDto response = new DiagnosticoDto();
        response.setId(guardado.getId());
        response.setCitaId(guardado.getCita().getCitaId());
        response.setNombre(guardado.getNombre());
        response.setDescripcion(guardado.getDescripcion());
        response.setEstadoDiagnostico(guardado.isEstadoDiagnostico());
        response.setCreadoAt(guardado.getCreadoAt());

        return response;
    }

    @Override
    public DiagnosticoDto editar(Long id, DiagnosticoDto dto) {
        Diagnostico diagnostico = diagnosticoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Diagnóstico no encontrado"));

        // Solo actualizar nombre y descripcion si vienen en el DTO
        if (dto.getNombre() != null) {
            diagnostico.setNombre(dto.getNombre());
        }
        if (dto.getDescripcion() != null) {
            diagnostico.setDescripcion(dto.getDescripcion());
        }

        Diagnostico actualizado = diagnosticoRepository.save(diagnostico);

        // Construcción manual del DTO
        DiagnosticoDto respuesta = new DiagnosticoDto();
        respuesta.setId(actualizado.getId());
        respuesta.setCitaId(actualizado.getCita().getCitaId());
        respuesta.setNombre(actualizado.getNombre());
        respuesta.setDescripcion(actualizado.getDescripcion());
        respuesta.setEstadoDiagnostico(actualizado.isEstadoDiagnostico());
        respuesta.setCreadoAt(actualizado.getCreadoAt());

        return respuesta;
    }

    @Override
    public DiagnosticoDto cambiarEstado(Long id, boolean estadoDiagnostico) {
        Diagnostico diagnostico = diagnosticoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Diagnóstico no encontrado"));

        diagnostico.setEstadoDiagnostico(estadoDiagnostico);

        Diagnostico actualizado = diagnosticoRepository.save(diagnostico);

        DiagnosticoDto respuesta = new DiagnosticoDto();
        respuesta.setId(actualizado.getId());
        respuesta.setCitaId(actualizado.getCita().getCitaId());
        respuesta.setNombre(actualizado.getNombre());
        respuesta.setDescripcion(actualizado.getDescripcion());
        respuesta.setEstadoDiagnostico(actualizado.isEstadoDiagnostico());
        respuesta.setCreadoAt(actualizado.getCreadoAt());

        return respuesta;
    }

}

