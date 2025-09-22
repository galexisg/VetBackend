package com.Vet.VetBackend.consulta.app.implementations;

import com.Vet.VetBackend.consulta.app.services.ConsultaService;
import com.Vet.VetBackend.consulta.domain.Consulta;
import com.Vet.VetBackend.consulta.repo.ConsultaDiagnosticoRepository;
import com.Vet.VetBackend.consulta.repo.ConsultaRepository;
import com.Vet.VetBackend.consulta.web.dto.ConsultaDto;
import com.Vet.VetBackend.consulta.web.dto.DiagnosticoDto;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Service
public class ConsultaServiceImpl implements ConsultaService {

    private final ConsultaRepository consultaRepository;
    private final ConsultaDiagnosticoRepository consultaDiagnosticoRepository;
    private final ModelMapper modelMapper;

    public ConsultaServiceImpl(ConsultaRepository consultaRepository,
                               ConsultaDiagnosticoRepository consultaDiagnosticoRepository,
                               ModelMapper modelMapper) {
        this.consultaRepository = consultaRepository;
        this.consultaDiagnosticoRepository = consultaDiagnosticoRepository;
        this.modelMapper = modelMapper;
    }

    private ConsultaDto mapToDto(Consulta consulta) {
        ConsultaDto dto = new ConsultaDto();
        dto.setId(consulta.getId());
        dto.setHistorialId(consulta.getHistorial().getId());
        dto.setObservaciones(consulta.getObservaciones());
        dto.setRecomendaciones(consulta.getRecomendaciones());

        // Manejo seguro de diagnósticos
        List<DiagnosticoDto> diagnosticos = (consulta.getConsultaDiagnosticos() != null)
                ? consulta.getConsultaDiagnosticos().stream()
                .map(cd -> {
                    DiagnosticoDto d = new DiagnosticoDto();
                    d.setId(cd.getDiagnostico().getId());
                    d.setNombre(cd.getDiagnostico().getNombre());
                    d.setDescripcion(cd.getDiagnostico().getDescripcion());
                    d.setObservacion(cd.getObservacion());
                    return d;
                })
                .collect(Collectors.toList())
                : new ArrayList<>();

        dto.setDiagnosticos(diagnosticos);
        return dto;
    }

    @Override
    public CompletableFuture<ConsultaDto> crearConsulta(ConsultaDto dto) {
        return CompletableFuture.supplyAsync(() -> {
            Consulta entity = modelMapper.map(dto, Consulta.class);
            entity = consultaRepository.save(entity);
            return mapToDto(entity);
        });
    }

    @Override
    public CompletableFuture<ConsultaDto> actualizarConsulta(Long id, ConsultaDto dto) {
        return CompletableFuture.supplyAsync(() -> {
            Consulta entity = consultaRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Consulta no encontrada"));
            // actualzar campos
            entity.setObservaciones(dto.getObservaciones());
            entity.setRecomendaciones(dto.getRecomendaciones());

            Consulta actualizada = consultaRepository.save(entity);
            return mapToDto(actualizada);
        });
    }

    @Override
    public CompletableFuture<ConsultaDto> obtenerPorId(Long id) {
        return CompletableFuture.supplyAsync(() -> {
            Consulta consulta = consultaRepository.findByIdWithDiagnosticos(id);
            if (consulta == null) {
                throw new RuntimeException("Consulta no encontrada con id: " + id);
            }
            return mapToDto(consulta);
        });
    }

    @Override
    public CompletableFuture<List<ConsultaDto>> obtenerTodas() {
        return CompletableFuture.supplyAsync(() ->
                consultaRepository.findAllWithDiagnosticos()
                        .stream()
                        .map(this::mapToDto)
                        .collect(Collectors.toList())
        );
    }

    @Override
    public CompletableFuture<List<ConsultaDto>> obtenerPorHistorial(Long historialId) {
        return CompletableFuture.supplyAsync(() ->
                consultaRepository.findByHistorialIdWithDiagnosticos(historialId)
                        .stream()
                        .map(this::mapToDto)
                        .collect(Collectors.toList())
        );
    }
}
