package com.Vet.VetBackend.consulta.app.implementations;

import com.Vet.VetBackend.consulta.app.services.ConsultaService;
import com.Vet.VetBackend.consulta.domain.Consulta;
import com.Vet.VetBackend.consulta.repo.ConsultaDiagnosticoRepository;
import com.Vet.VetBackend.consulta.repo.ConsultaRepository;
import com.Vet.VetBackend.consulta.web.dto.ConsultaDto;
import com.Vet.VetBackend.consulta.web.dto.DiagnosticoDto;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

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

    private ConsultaDto mapToDto(Consulta entity) {
        ConsultaDto dto = modelMapper.map(entity, ConsultaDto.class);
        List<DiagnosticoDto> diags = consultaDiagnosticoRepository.findAll()
                .stream()
                .filter(cd -> cd.getConsulta().getId().equals(entity.getId()))
                .map(cd -> {
                    DiagnosticoDto d = modelMapper.map(cd.getDiagnostico(), DiagnosticoDto.class);
                    d.setObservacion(cd.getObservacion());
                    return d;
                })
                .collect(Collectors.toList());
        dto.setDiagnosticos(diags);
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
            modelMapper.map(dto, entity);
            entity = consultaRepository.save(entity);
            return mapToDto(entity);
        });
    }

    @Override
    public CompletableFuture<ConsultaDto> obtenerPorId(Long id) {
        return CompletableFuture.supplyAsync(() -> mapToDto(
                consultaRepository.findById(id)
                        .orElseThrow(() -> new RuntimeException("Consulta no encontrada"))
        ));
    }

    @Override
    public CompletableFuture<List<ConsultaDto>> obtenerTodas() {
        return CompletableFuture.supplyAsync(() ->
                consultaRepository.findAll().stream().map(this::mapToDto).collect(Collectors.toList()));
    }

    @Override
    public CompletableFuture<List<ConsultaDto>> obtenerPorHistorial(Long historialId) {
        return CompletableFuture.supplyAsync(() ->
                consultaRepository.findAll().stream()
                        .filter(c -> c.getHistorial().getId().equals(historialId))
                        .map(this::mapToDto)
                        .collect(Collectors.toList()));
    }
}
