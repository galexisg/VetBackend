package com.Vet.VetBackend.consulta.app.implementations;

import com.Vet.VetBackend.consulta.app.services.DiagnosticoService;
import com.Vet.VetBackend.consulta.domain.Diagnostico;
import com.Vet.VetBackend.consulta.repo.DiagnosticoRepository;
import com.Vet.VetBackend.consulta.web.dto.DiagnosticoDto;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Service
public class DiagnosticoServiceImpl implements DiagnosticoService {
    private final DiagnosticoRepository diagnosticoRepository;
    private final ModelMapper modelMapper;

    public DiagnosticoServiceImpl(DiagnosticoRepository diagnosticoRepository, ModelMapper modelMapper) {
        this.diagnosticoRepository = diagnosticoRepository;
        this.modelMapper = modelMapper;
    }

    private DiagnosticoDto mapToDto(Diagnostico entity) {
        return modelMapper.map(entity, DiagnosticoDto.class);
    }

    @Override
    public CompletableFuture<DiagnosticoDto> crearDiagnostico(DiagnosticoDto dto) {
        return CompletableFuture.supplyAsync(() -> {
            Diagnostico entity = modelMapper.map(dto, Diagnostico.class);
            entity = diagnosticoRepository.save(entity);
            return mapToDto(entity);
        });
    }

    @Override
    public CompletableFuture<DiagnosticoDto> actualizarDiagnostico(Long id, DiagnosticoDto dto) {
        return CompletableFuture.supplyAsync(() -> {
            Diagnostico entity = diagnosticoRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Diagnóstico no encontrado"));
            modelMapper.map(dto, entity);
            entity = diagnosticoRepository.save(entity);
            return mapToDto(entity);
        });
    }

    @Override
    public CompletableFuture<DiagnosticoDto> obtenerPorId(Long id) {
        return CompletableFuture.supplyAsync(() ->
                mapToDto(diagnosticoRepository.findById(id)
                        .orElseThrow(() -> new RuntimeException("Diagnóstico no encontrado"))));
    }

    @Override
    public CompletableFuture<List<DiagnosticoDto>> obtenerTodos() {
        return CompletableFuture.supplyAsync(() ->
                diagnosticoRepository.findAll().stream().map(this::mapToDto).collect(Collectors.toList()));
    }
}
