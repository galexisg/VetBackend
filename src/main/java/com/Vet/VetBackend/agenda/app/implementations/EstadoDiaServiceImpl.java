package com.Vet.VetBackend.agenda.app.implementations;

import com.Vet.VetBackend.agenda.app.services.IEstadoDiaService;
import com.Vet.VetBackend.agenda.domain.EstadoDia;
import com.Vet.VetBackend.agenda.repo.EstadoDiaRepository;
import com.Vet.VetBackend.agenda.web.dto.EstadoDiaGuardarReq;
import com.Vet.VetBackend.agenda.web.dto.EstadoDiaModificarReq;
import com.Vet.VetBackend.agenda.web.dto.EstadoDiaSalidaRes;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EstadoDiaServiceImpl implements IEstadoDiaService {

    private final EstadoDiaRepository estadoDiaRepository;

    @Override
    public List<EstadoDiaSalidaRes> findAll() {
        return estadoDiaRepository.findAll().stream()
                .map(this::toSalidaDTO)
                .collect(Collectors.toList());
    }

    @Override
    public EstadoDiaSalidaRes findById(int estadoDiaId) {
        EstadoDia estado = estadoDiaRepository.findById(estadoDiaId)
                .orElseThrow(() -> new EntityNotFoundException("Estado no encontrado con ID: " + estadoDiaId));
        return toSalidaDTO(estado);
    }

    @Override
    public EstadoDiaSalidaRes save(EstadoDiaGuardarReq estadoDiaDTO) {
        EstadoDia estado = EstadoDia.builder()
                .estado(estadoDiaDTO.getEstado())
                .build();

        return toSalidaDTO(estadoDiaRepository.save(estado));
    }

    @Override
    public EstadoDiaSalidaRes update(int estadoDiaId, EstadoDiaModificarReq estadoDiaDTO) {
        EstadoDia estadoExistente = estadoDiaRepository.findById(estadoDiaId)
                .orElseThrow(() -> new EntityNotFoundException("Estado no encontrado con ID: " + estadoDiaId));

        estadoExistente.setEstado(estadoDiaDTO.getEstado());

        return toSalidaDTO(estadoDiaRepository.save(estadoExistente));
    }

    @Override
    public void deleteById(int estadoDiaId) {
        if (!estadoDiaRepository.existsById(estadoDiaId)) {
            throw new EntityNotFoundException("Estado no encontrado con ID: " + estadoDiaId);
        }
        estadoDiaRepository.deleteById(estadoDiaId);
    }

    private EstadoDiaSalidaRes toSalidaDTO(EstadoDia estadoDia) {
        return EstadoDiaSalidaRes.builder()
                .estadoDiaId(estadoDia.getEstadoDiaId())
                .estado(estadoDia.getEstado().name()) // ⚠️ aquí usamos .name() para convertir enum a String
                .build();
    }

}