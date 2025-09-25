package com.Vet.VetBackend.historialvacuna.app.implementations;

import com.Vet.VetBackend.historialvacuna.app.domain.HistorialVacuna;
import com.Vet.VetBackend.historialvacuna.app.repo.HistorialVacunaRepository;
import com.Vet.VetBackend.historialvacuna.app.services.HistorialVacunaService;
import com.Vet.VetBackend.historialvacuna.web.dto.CreateHistorialVacunaDTO;
import com.Vet.VetBackend.historialvacuna.web.dto.HistorialVacunaDTO;
import com.Vet.VetBackend.vacuna.domain.Vacuna;
import com.Vet.VetBackend.vacuna.repo.VacunaRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class HistorialVacunaImpl implements HistorialVacunaService {

    private final HistorialVacunaRepository historialVacunaRepository;
    private final VacunaRepository vacunaRepository;

    @Override
    public HistorialVacunaDTO guardar(CreateHistorialVacunaDTO dto) {
        // findById espera Integer
        Vacuna vacuna = vacunaRepository.findById(toInt(dto.getVacunaId()))
                .orElseThrow(() -> new EntityNotFoundException(
                        "Vacuna no encontrada con ID: " + dto.getVacunaId()));

        // El DTO actual NO tiene veterinarioId/medicamentoId/loteId/observacion (según tus errores).
        // Usamos solo lo que sí existe: mascotaId, vacunaId, fecha y (si existe) observaciones/observacion.
        HistorialVacuna historial = HistorialVacuna.builder()
                .mascotaId(toInt(dto.getMascotaId()))
                .vacuna(vacuna)
                .fecha(dto.getFecha())
                .observacion(resolveObservacion(dto)) // devuelve null si no existe el campo
                .build();

        historial = historialVacunaRepository.save(historial);

        return HistorialVacunaDTO.builder()
                .historialVacunaId(historial.getHistorialVacunaId())
                .vacunaId(historial.getVacuna() != null ? historial.getVacuna().getVacunaId() : null)
                .mascotaId(historial.getMascotaId())
                .fecha(historial.getFecha())
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
                        .vacunaId(h.getVacuna() != null ? h.getVacuna().getVacunaId() : null)
                        .fecha(h.getFecha())
                        .observacion(h.getObservacion())
                        .build())
                .collect(Collectors.toList());
    }

    private static Integer toInt(Long v) {
        return v == null ? null : Math.toIntExact(v);
    }

    // Soporta ambos nombres 'observaciones' o 'observacion' si uno de los dos existe en el DTO
    private static String resolveObservacion(CreateHistorialVacunaDTO dto) {
        try {
            var m = dto.getClass().getMethod("getObservaciones");
            Object val = m.invoke(dto);
            return val != null ? String.valueOf(val) : null;
        } catch (Exception ignore) {
            try {
                var m2 = dto.getClass().getMethod("getObservacion");
                Object val2 = m2.invoke(dto);
                return val2 != null ? String.valueOf(val2) : null;
            } catch (Exception ignore2) {
                return null;
            }
        }
    }
}
