package com.Vet.VetBackend.agenda.app.implementations;

import com.Vet.VetBackend.agenda.app.services.IDiaService;
import com.Vet.VetBackend.agenda.domain.Dia;
import com.Vet.VetBackend.agenda.domain.EstadoDia;
import com.Vet.VetBackend.agenda.repo.DiaRepository;
import com.Vet.VetBackend.agenda.repo.EstadoDiaRepository;
import com.Vet.VetBackend.agenda.web.dto.*;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DiaServiceImpl implements IDiaService {

    private final DiaRepository diaRepository;
    private final EstadoDiaRepository estadoDiaRepository;

    @Override
    public List<DiaSalidaRes> findAll() {
        return diaRepository.findAll().stream()
                .map(this::toSalidaDTO)
                .toList();
    }

    @Override
    public DiaSalidaRes findById(int diaId) {
        Dia dia = diaRepository.findById(diaId)
                .orElseThrow(() -> new EntityNotFoundException("Día no encontrado con ID: " + diaId));
        return toSalidaDTO(dia);
    }

    @Override
    public DiaSalidaRes save(DiaGuardarReq diaDTO) {
        EstadoDia estado = (EstadoDia) estadoDiaRepository.findById(diaDTO.getEstadoDiaId())
                .orElseThrow(() -> new EntityNotFoundException("Estado de día no encontrado con ID: " + diaDTO.getEstadoDiaId()));

        Dia dia = Dia.builder()
                .nombre(diaDTO.getNombre())
                .estadoDia(estado)
                .build();

        return toSalidaDTO(diaRepository.save(dia));
    }

    @Override
    public DiaSalidaRes update(int diaId, DiaModificarReq diaDTO) {
        Dia diaExistente = diaRepository.findById(diaId)
                .orElseThrow(() -> new EntityNotFoundException("Día no encontrado con ID: " + diaId));

        EstadoDia estado = (EstadoDia) estadoDiaRepository.findById(diaDTO.getEstadoDiaId())
                .orElseThrow(() -> new EntityNotFoundException("Estado de día no encontrado con ID: " + diaDTO.getEstadoDiaId()));

        diaExistente.setNombre(diaDTO.getNombre());
        diaExistente.setEstadoDia(estado);

        return toSalidaDTO(diaRepository.save(diaExistente));
    }

    @Override
    public void deleteById(int diaId) {
        if (!diaRepository.existsById(diaId)) {
            throw new EntityNotFoundException("Día no encontrado con ID: " + diaId);
        }
        diaRepository.deleteById(diaId);
    }

    private DiaSalidaRes toSalidaDTO(Dia dia) {
        EstadoDiaSalidaRes estadoDto = EstadoDiaSalidaRes.builder()
                .estadoDiaId(dia.getEstadoDia().getEstadoDiaId())
                .nombre(dia.getEstadoDia().getNombre())
                .build();

        return DiaSalidaRes.builder()
                .diaId(dia.getDiaId())
                .nombre(dia.getNombre())
                .estadoDia(estadoDto)
                .build();
    }
}
