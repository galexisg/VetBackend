package com.Vet.VetBackend.historialvacuna.app.implementations;

import com.Vet.VetBackend.historialvacuna.app.domain.HistorialVacuna;
import com.Vet.VetBackend.historialvacuna.app.repo.HistorialVacunaRepository;
import com.Vet.VetBackend.historialvacuna.app.services.HistorialVacunaService;
import com.Vet.VetBackend.historialvacuna.web.dto.CreateHistorialVacunaDTO;
import com.Vet.VetBackend.historialvacuna.web.dto.HistorialVacunaDTO;
import com.Vet.VetBackend.vacuna.repo.VacunaRepository;
import com.Vet.VetBackend.vacuna.domain.Vacuna;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import jakarta.persistence.EntityNotFoundException;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class HistorialVacunaImpl implements HistorialVacunaService {

    private final HistorialVacunaRepository historialVacunaRepository;
    private final VacunaRepository vacunaRepository;

    @Override
    public HistorialVacunaDTO guardar(CreateHistorialVacunaDTO dto) {
        Vacuna vacuna = vacunaRepository.findById(dto.getVacunaId())
                .orElseThrow(() -> new EntityNotFoundException("Vacuna no encontrada con ID: " + dto.getVacunaId()));

        HistorialVacuna historial = HistorialVacuna.builder()
                .mascotaId(dto.getMascotaId())
                .veterinarioId(dto.getVeterinarioId())
                .vacuna(vacuna)
                .fecha(dto.getFecha())
                .medicamentoId(dto.getMedicamentoId())
                .loteId(dto.getLoteId())
                .observacion(dto.getObservacion())
                .build();

        historial = historialVacunaRepository.save(historial);

        return HistorialVacunaDTO.builder()
                .historialVacunaId(historial.getHistorialVacunaId())
                .vacunaId(historial.getVacuna().getVacunaId())
                .mascotaId(historial.getMascotaId())
                .veterinarioId(historial.getVeterinarioId())
                .fecha(historial.getFecha())
                .medicamentoId(historial.getMedicamentoId())
                .loteId(historial.getLoteId())
                .observacion(historial.getObservacion())
                .build();
    }

    @Override
    public List<HistorialVacunaDTO> listarPorMascota(Integer mascotaId) {
        return historialVacunaRepository.findByMascotaId(mascotaId)
                .stream()
                .map(h -> HistorialVacunaDTO.builder()
                        .historialVacunaId(h.getHistorialVacunaId())
                        .mascotaId(h.getMascotaId())
                        .veterinarioId(h.getVeterinarioId())
                        .vacunaId(h.getVacuna().getVacunaId())
                        .fecha(h.getFecha())
                        .medicamentoId(h.getMedicamentoId())
                        .loteId(h.getLoteId())
                        .observacion(h.getObservacion())
                        .build())
                .collect(Collectors.toList());
    }
}
