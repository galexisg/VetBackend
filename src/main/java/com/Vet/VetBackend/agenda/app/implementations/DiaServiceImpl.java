package com.Vet.VetBackend.agenda.app.implementations;

import com.Vet.VetBackend.agenda.app.services.IDiaService;
import com.Vet.VetBackend.agenda.domain.Dia;
import com.Vet.VetBackend.agenda.domain.EstadoDia;
import com.Vet.VetBackend.agenda.repo.DiaRepository;
import com.Vet.VetBackend.agenda.repo.EstadoDiaRepository;
import com.Vet.VetBackend.agenda.web.dto.DiaGuardarReq;
import com.Vet.VetBackend.agenda.web.dto.DiaModificarReq;
import com.Vet.VetBackend.agenda.web.dto.DiaSalidaRes;
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
        // Convertir String a Enum de forma segura
        EstadoDia.Estado estadoEnum;
        try {
            estadoEnum = EstadoDia.Estado.valueOf(diaDTO.getEstadoDia());
        } catch (IllegalArgumentException e) {
            throw new EntityNotFoundException("Estado inválido: " + diaDTO.getEstadoDia());
        }

        EstadoDia estadoDia = estadoDiaRepository.findByEstado(estadoEnum)
                .orElseThrow(() -> new EntityNotFoundException("Estado de día no encontrado: " + estadoEnum));

        Dia dia = Dia.builder()
                .nombre(diaDTO.getNombre())
                .estadoDia(estadoDia)
                .build();

        return toSalidaDTO(diaRepository.save(dia));
    }

    @Override
    public DiaSalidaRes update(int diaId, DiaModificarReq diaDTO) {
        Dia diaExistente = diaRepository.findById(diaId)
                .orElseThrow(() -> new EntityNotFoundException("Día no encontrado con ID: " + diaId));

        // Convertir String a Enum
        EstadoDia.Estado estadoEnum;
        try {
            estadoEnum = EstadoDia.Estado.valueOf(diaDTO.getEstadoDia());
        } catch (IllegalArgumentException e) {
            throw new EntityNotFoundException("Estado inválido: " + diaDTO.getEstadoDia());
        }

        EstadoDia estadoDia = estadoDiaRepository.findByEstado(estadoEnum)
                .orElseThrow(() -> new EntityNotFoundException("Estado de día no encontrado: " + estadoEnum));

        diaExistente.setNombre(diaDTO.getNombre());
        diaExistente.setEstadoDia(estadoDia);

        return toSalidaDTO(diaRepository.save(diaExistente));
    }

    @Override
    public void deleteById(int diaId) {
        if (!diaRepository.existsById(diaId)) {
            throw new EntityNotFoundException("Día no encontrado con ID: " + diaId);
        }
        diaRepository.deleteById(diaId);
    }

    // DTO de salida simplificado: devuelve solo el nombre del día y el estado
    private DiaSalidaRes toSalidaDTO(Dia dia) {
        return DiaSalidaRes.builder()
                .diaId(dia.getDiaId())
                .nombre(dia.getNombre())
                .estado(dia.getEstadoDia().getEstado().name()) // Solo el nombre del enum como String
                .build();
    }
}
