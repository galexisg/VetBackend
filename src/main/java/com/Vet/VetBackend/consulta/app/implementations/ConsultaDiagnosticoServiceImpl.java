package com.Vet.VetBackend.consulta.app.implementations;

import com.Vet.VetBackend.consulta.app.services.ConsultaDiagnosticoService;
import com.Vet.VetBackend.consulta.domain.Consulta;
import com.Vet.VetBackend.consulta.domain.ConsultaDiagnostico;
import com.Vet.VetBackend.consulta.domain.Diagnostico;
import com.Vet.VetBackend.consulta.repo.ConsultaDiagnosticoRepository;
import com.Vet.VetBackend.consulta.repo.ConsultaRepository;
import com.Vet.VetBackend.consulta.repo.DiagnosticoRepository;
import com.Vet.VetBackend.consulta.web.dto.ConsultaDiagnosticoDto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Service
public class ConsultaDiagnosticoServiceImpl  implements ConsultaDiagnosticoService {
    private final ConsultaDiagnosticoRepository consultaDiagnosticoRepository;
    private final ConsultaRepository consultaRepository;
    private final DiagnosticoRepository diagnosticoRepository;

    public ConsultaDiagnosticoServiceImpl(ConsultaDiagnosticoRepository consultaDiagnosticoRepository,
                                          ConsultaRepository consultaRepository,
                                          DiagnosticoRepository diagnosticoRepository) {
        this.consultaDiagnosticoRepository = consultaDiagnosticoRepository;
        this.consultaRepository = consultaRepository;
        this.diagnosticoRepository = diagnosticoRepository;
    }

    private ConsultaDiagnosticoDto mapToDto(ConsultaDiagnostico entity) {
        ConsultaDiagnosticoDto dto = new ConsultaDiagnosticoDto();
        dto.setId(entity.getId());
        dto.setConsultaId(entity.getConsulta().getId());
        dto.setDiagnosticoId(entity.getDiagnostico().getId());
        dto.setDiagnosticoNombre(entity.getDiagnostico().getNombre());
        dto.setObservacion(entity.getObservacion());
        return dto;
    }

    @Override
    public CompletableFuture<ConsultaDiagnosticoDto> crear(ConsultaDiagnosticoDto dto) {
        return CompletableFuture.supplyAsync(() -> {
            Consulta consulta = consultaRepository.findById(dto.getConsultaId())
                    .orElseThrow(() -> new RuntimeException("Consulta no encontrada"));
            Diagnostico diagnostico = diagnosticoRepository.findById(dto.getDiagnosticoId())
                    .orElseThrow(() -> new RuntimeException("Diagnóstico no encontrado"));

            ConsultaDiagnostico entity = new ConsultaDiagnostico();
            entity.setConsulta(consulta);
            entity.setDiagnostico(diagnostico);
            entity.setObservacion(dto.getObservacion());

            entity = consultaDiagnosticoRepository.save(entity);
            return mapToDto(entity);
        });
    }

    @Override
    public CompletableFuture<List<ConsultaDiagnosticoDto>> obtenerPorConsulta(Long consultaId) {
        return CompletableFuture.supplyAsync(() ->
                consultaDiagnosticoRepository.findWithDiagnosticoByConsultaId(consultaId)
                        .stream().map(this::mapToDto).collect(Collectors.toList()));
    }

    @Override
    public CompletableFuture<Void> eliminar(Long id) {
        return CompletableFuture.runAsync(() -> consultaDiagnosticoRepository.deleteById(id));
    }
}
