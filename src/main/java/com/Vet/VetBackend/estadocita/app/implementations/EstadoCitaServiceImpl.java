package com.Vet.VetBackend.estadocita.app.implementations;

import com.Vet.VetBackend.estadocita.app.services.IEstadoService;
import com.Vet.VetBackend.estadocita.domain.EstadoCita;
import com.Vet.VetBackend.estadocita.repo.IEstadoRepository;
import com.Vet.VetBackend.estadocita.web.dto.EstadoGuardar;
import com.Vet.VetBackend.estadocita.web.dto.EstadoModificar;
import com.Vet.VetBackend.estadocita.web.dto.EstadoSalida;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EstadoCitaServiceImpl implements IEstadoService {

    private final IEstadoRepository estadoRepository;

    @Override
    public List<EstadoSalida> obtenerTodos() {
        return estadoRepository.findAll().stream()
                .map(this::toSalidaDTO)
                .collect(Collectors.toList());
    }

    @Override
    public Page<EstadoSalida> obtenerTodosPaginados(Pageable pageable) {
        Page<EstadoCita> page = estadoRepository.findAll(pageable);

        List<EstadoSalida> dtoList = page.stream()
                .map(this::toSalidaDTO)
                .collect(Collectors.toList());

        return new PageImpl<>(dtoList, pageable, page.getTotalElements());
    }

    @Override
    public EstadoSalida obtenerPorId(Integer id) {
        EstadoCita estado = estadoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("EstadoCita no encontrado con ID: " + id));
        return toSalidaDTO(estado);
    }

    @Override
    public EstadoSalida crear(EstadoGuardar dto) {
        EstadoCita estado = EstadoCita.builder()
                .nombre(dto.getNombre())
                .build();

        EstadoCita guardado = estadoRepository.save(estado);
        return toSalidaDTO(guardado);
    }

    @Override
    public EstadoSalida editar(EstadoModificar dto) {
        EstadoCita estado = estadoRepository.findById(dto.getId())
                .orElseThrow(() -> new RuntimeException("EstadoCita no encontrado con ID: " + dto.getId()));

        estado.setNombre(dto.getNombre());

        EstadoCita actualizado = estadoRepository.save(estado);
        return toSalidaDTO(actualizado);
    }

    // 🔹 Método privado para convertir entidad -> DTO
    private EstadoSalida toSalidaDTO(EstadoCita estado) {
        EstadoSalida dto = new EstadoSalida();
        dto.setId(estado.getId());
        dto.setNombre(estado.getNombre());
        return dto;
    }
}
