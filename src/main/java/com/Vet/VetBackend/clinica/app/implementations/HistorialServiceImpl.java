package com.Vet.VetBackend.clinica.app.implementations;

import com.Vet.VetBackend.clinica.app.services.HistorialService;
import com.Vet.VetBackend.clinica.domain.Historial;
import com.Vet.VetBackend.clinica.repo.HistorialRepository;
import com.Vet.VetBackend.clinica.web.dto.HistorialDto;
import com.Vet.VetBackend.consulta.domain.Diagnostico;
import com.Vet.VetBackend.consulta.repo.ConsultaDiagnosticoRepository;
import com.Vet.VetBackend.consulta.repo.ConsultaRepository;
import com.Vet.VetBackend.consulta.repo.DiagnosticoRepository;
import com.Vet.VetBackend.consulta.web.dto.ConsultaDto;
import com.Vet.VetBackend.consulta.web.dto.DiagnosticoDto;
import com.Vet.VetBackend.tratamientos.repo.TratamientoAplicadoRepository;
import com.Vet.VetBackend.tratamientos.web.dto.TratamientoAplicadoRes;
import org.springframework.scheduling.annotation.Async;
import org.springframework.transaction.annotation.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Service
public class HistorialServiceImpl implements HistorialService {
    private final HistorialRepository historialRepository;
    private final TratamientoAplicadoRepository tratamientoAplicadoRepository;
    private final ModelMapper modelMapper;
    private final ConsultaRepository consultaRepository;
    private final ConsultaDiagnosticoRepository consultaDiagnosticoRepository;

    public HistorialServiceImpl(HistorialRepository historialRepository,
                                TratamientoAplicadoRepository tratamientoAplicadoRepository,
                                ModelMapper modelMapper , ConsultaRepository consultaRepository,
                                ConsultaDiagnosticoRepository consultaDiagnosticoRepository) {
        this.historialRepository = historialRepository;
        this.tratamientoAplicadoRepository = tratamientoAplicadoRepository;
        this.modelMapper = modelMapper;
        this.consultaRepository = consultaRepository;
        this.consultaDiagnosticoRepository = consultaDiagnosticoRepository;
    }

    private HistorialDto mapToDTO(Historial entity) {
        HistorialDto dto = modelMapper.map(entity, HistorialDto.class);
        List<TratamientoAplicadoRes> tratamientos = tratamientoAplicadoRepository.findByHistorialId(entity.getId())
                .stream()
                .map(TratamientoAplicadoRes::fromEntity)
                .collect(Collectors.toList());
        dto.setTratamientosAplicados(tratamientos);

        // Traer consultas con diagnósticos
        List<ConsultaDto> consultas = consultaRepository.findByHistorialIdWithDiagnosticos(entity.getId())
                .stream()
                .map(c -> {
                    ConsultaDto consultaDto = modelMapper.map(c, ConsultaDto.class);
                    consultaDto.setHistorialId(entity.getId());

                    // Diagnósticos de esta consulta
                    List<DiagnosticoDto> diagnosticos = consultaDiagnosticoRepository.findWithDiagnosticoByConsultaId(c.getId())
                            .stream()
                            .map(cd -> {
                                Diagnostico d = cd.getDiagnostico(); // ✅ ya viene cargado
                                DiagnosticoDto dd = new DiagnosticoDto();
                                dd.setId(d.getId());
                                dd.setNombre(d.getNombre());
                                dd.setDescripcion(d.getDescripcion());
                                return dd;
                            })
                            .collect(Collectors.toList());

                    consultaDto.setDiagnosticos(diagnosticos);
                    return consultaDto;
                })
                .collect(Collectors.toList());

        dto.setConsultas(consultas);
        return dto;
    }

    @Override
    public CompletableFuture<HistorialDto> crearHistorial(HistorialDto historialDTO) {
        return CompletableFuture.supplyAsync(() -> {
            Historial entity = modelMapper.map(historialDTO, Historial.class);

            entity = historialRepository.save(entity);
            return mapToDTO(entity);
        });
    }


    @Async
    @Override
    public CompletableFuture<List<HistorialDto>> obtenerTodos() {
        return CompletableFuture.supplyAsync(() ->
                historialRepository.findAll()
                        .stream()
                        .map(this::mapToDTO)
                        .collect(Collectors.toList())
        );
    }

    @Override
    public CompletableFuture<HistorialDto> obtenerPorId(Long id) {
        return CompletableFuture.supplyAsync(() -> {
            Historial entity = historialRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Historial no encontrado con id: " + id));
            return mapToDTO(entity);
        });
    }

    @Override
    public CompletableFuture<List<HistorialDto>> obtenerHistorialesPorMascota(Integer mascotaId) {
        return CompletableFuture.supplyAsync(() ->
                historialRepository.findByMascotaId(mascotaId)
                        .stream()
                        .map(this::mapToDTO)
                        .collect(Collectors.toList())
        );
    }
}



