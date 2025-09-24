package com.Vet.VetBackend.agenda.app.implementations;

import com.Vet.VetBackend.agenda.app.services.IDetalleHorarioVeterinarioService;
import com.Vet.VetBackend.agenda.web.dto.DetalleHorarioVeterinarioGuardarReq;
import com.Vet.VetBackend.agenda.web.dto.DetalleHorarioVeterinarioModificarReq;
import com.Vet.VetBackend.agenda.web.dto.DetalleHorarioVeterinarioSalidaRes;
import com.Vet.VetBackend.agenda.repo.DetalleHorarioVeterinarioRepository;
import com.Vet.VetBackend.agenda.repo.DiaRepository;
import com.Vet.VetBackend.agenda.repo.BloqueHorarioRepository;
import com.Vet.VetBackend.agenda.domain.Dia;
import com.Vet.VetBackend.agenda.domain.DetalleHorarioVeterinario;
import com.Vet.VetBackend.agenda.domain.BloqueHorario;
import com.Vet.VetBackend.veterinario.domain.Veterinario;
import com.Vet.VetBackend.veterinario.repo.VeterinarioRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DetalleHorarioVeterinariaServiceImpl implements IDetalleHorarioVeterinarioService {

    private final DetalleHorarioVeterinarioRepository detalleHorarioVeterinarioRepository;
    private final VeterinarioRepository veterinarioRepository;
    private final DiaRepository diaRepository;
    private final BloqueHorarioRepository bloqueHorarioRepository;

    @Override
    @Transactional(readOnly = true)
    public List<DetalleHorarioVeterinarioSalidaRes> findAll() {
        return detalleHorarioVeterinarioRepository.findAll().stream()
                .map(this::convertirA_DtoSalida)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public DetalleHorarioVeterinarioSalidaRes findById(Integer id) {
        return detalleHorarioVeterinarioRepository.findById(id)
                .map(this::convertirA_DtoSalida)
                .orElseThrow(() -> new EntityNotFoundException("DetalleHorarioVeterinario no encontrado"));
    }

    @Override
    @Transactional
    public DetalleHorarioVeterinarioSalidaRes save(DetalleHorarioVeterinarioGuardarReq detalleDTO) {
        Veterinario veterinario = veterinarioRepository.findById(detalleDTO.getVeterinarioId())
                .orElseThrow(() -> new EntityNotFoundException("Veterinario no encontrado"));

        Dia dia = diaRepository.findById(detalleDTO.getDiaId())
                .orElseThrow(() -> new EntityNotFoundException("Día no encontrado"));

        BloqueHorario bloqueHorario = bloqueHorarioRepository.findById(detalleDTO.getBloqueHorarioId())
                .orElseThrow(() -> new EntityNotFoundException("Bloque horario no encontrado"));

        DetalleHorarioVeterinario detalle = DetalleHorarioVeterinario.builder()
                .veterinario(veterinario)
                .dia(dia)
                .bloqueHorario(bloqueHorario)
                .build();

        DetalleHorarioVeterinario guardado = detalleHorarioVeterinarioRepository.save(detalle);
        return convertirA_DtoSalida(guardado);
    }

    @Override
    @Transactional
    public DetalleHorarioVeterinarioSalidaRes update(Integer id, DetalleHorarioVeterinarioModificarReq detalleDTO) {
        DetalleHorarioVeterinario detalleExistente = detalleHorarioVeterinarioRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("DetalleHorarioVeterinario a actualizar no encontrado"));

        Veterinario veterinario = veterinarioRepository.findById(detalleDTO.getVeterinarioId())
                .orElseThrow(() -> new EntityNotFoundException("Veterinario no encontrado"));

        Dia dia = diaRepository.findById(detalleDTO.getDiaId())
                .orElseThrow(() -> new EntityNotFoundException("Día no encontrado"));

        BloqueHorario bloqueHorario = bloqueHorarioRepository.findById(detalleDTO.getBloqueHorarioId())
                .orElseThrow(() -> new EntityNotFoundException("Bloque horario no encontrado"));

        detalleExistente.setVeterinario(veterinario);
        detalleExistente.setDia(dia);
        detalleExistente.setBloqueHorario(bloqueHorario);

        DetalleHorarioVeterinario actualizado = detalleHorarioVeterinarioRepository.save(detalleExistente);
        return convertirA_DtoSalida(actualizado);
    }

    @Override
    @Transactional
    public void deleteById(Integer id) {
        if (!detalleHorarioVeterinarioRepository.existsById(id)) {
            throw new EntityNotFoundException("DetalleHorarioVeterinario no encontrado con ID: " + id);
        }
        detalleHorarioVeterinarioRepository.deleteById(id);
    }

    private DetalleHorarioVeterinarioSalidaRes convertirA_DtoSalida(DetalleHorarioVeterinario detalle) {
        return DetalleHorarioVeterinarioSalidaRes.builder()
                .detalleHorarioVeterinarioId(detalle.getDetalleHorarioVeterinarioId())
                .veterinarioId(detalle.getVeterinario() != null ? detalle.getVeterinario().getId() : null)
                .nombreVeterinario(detalle.getVeterinario() != null && detalle.getVeterinario().getUsuario() != null
                        ? detalle.getVeterinario().getUsuario().getNombreCompleto()
                        : null)
                .diaId(detalle.getDia() != null ? detalle.getDia().getDiaId() : null)
                .Dia(detalle.getDia() != null ? detalle.getDia().getNombre() : null)
                .estado(detalle.getDia() != null && detalle.getDia().getEstadoDia() != null
                        ? detalle.getDia().getEstadoDia().getEstado().name()   // 👈 Enum → String
                        : null)
                .bloqueHorarioId(detalle.getBloqueHorario() != null ? detalle.getBloqueHorario().getBloqueHorarioId() : null)
                .horaInicio(detalle.getBloqueHorario() != null ? detalle.getBloqueHorario().getInicio() : null)
                .horaFin(detalle.getBloqueHorario() != null ? detalle.getBloqueHorario().getFin() : null)
                .build();
    }



}
