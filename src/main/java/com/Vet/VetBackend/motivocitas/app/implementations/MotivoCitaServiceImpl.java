package com.Vet.VetBackend.motivocitas.app.implementations;

import com.Vet.VetBackend.motivocitas.domain.MotivoCita;
import com.Vet.VetBackend.motivocitas.web.dto.MotivoCitaRes;
import com.Vet.VetBackend.motivocitas.web.dto.MotivoCitaReq;
import com.Vet.VetBackend.motivocitas.repo.MotivoCitaRepository;
import com.Vet.VetBackend.motivocitas.app.services.MotivoCitaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MotivoCitaServiceImpl implements MotivoCitaService {

    @Autowired
    private MotivoCitaRepository motivoRepository;

    @Override
    public List<MotivoCitaRes> listarActivos() {
        return motivoRepository.findByActivoTrue()
                .stream()
                .map(MotivoCitaRes::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public MotivoCitaRes crear(MotivoCitaReq request) {
        // Validar nombre único
        Optional<MotivoCita> motivoExistente = motivoRepository.findByNombre(request.getNombre());
        if (motivoExistente.isPresent()) {
            throw new RuntimeException("Ya existe un motivo con ese nombre");
        }

        // Validar campo obligatorio
        if (request.getNombre() == null || request.getNombre().trim().isEmpty()) {
            throw new RuntimeException("El nombre del motivo es obligatorio");
        }

        // Crear entidad
        MotivoCita motivo = new MotivoCita(request.getNombre(), request.getDescripcion());
        MotivoCita motivoGuardado = motivoRepository.save(motivo);

        return MotivoCitaRes.fromEntity(motivoGuardado);
    }

    @Override
    public Optional<MotivoCitaRes> editar(Long id, MotivoCitaReq request) {
        Optional<MotivoCita> motivoExistente = motivoRepository.findById(id);

        if (motivoExistente.isPresent()) {
            MotivoCita motivo = motivoExistente.get();

            // Validar nombre único
            if (request.getNombre() != null &&
                    motivoRepository.existsByNombreAndIdNot(request.getNombre(), id)) {
                throw new RuntimeException("Ya existe otro motivo con ese nombre");
            }

            // Actualizar campos
            if (request.getNombre() != null) {
                motivo.setNombre(request.getNombre());
            }
            if (request.getDescripcion() != null) {
                motivo.setDescripcion(request.getDescripcion());
            }

            MotivoCita motivoActualizado = motivoRepository.save(motivo);
            return Optional.of(MotivoCitaRes.fromEntity(motivoActualizado));
        }

        return Optional.empty();
    }

    @Override
    public Optional<MotivoCitaRes> desactivar(Long id) {
        Optional<MotivoCita> motivoExistente = motivoRepository.findById(id);

        if (motivoExistente.isPresent()) {
            MotivoCita motivo = motivoExistente.get();
            motivo.setActivo(false);
            MotivoCita motivoDesactivado = motivoRepository.save(motivo);
            return Optional.of(MotivoCitaRes.fromEntity(motivoDesactivado));
        }

        return Optional.empty();
    }

    @Override
    public Optional<MotivoCitaRes> activar(Long id) {
        Optional<MotivoCita> motivoExistente = motivoRepository.findById(id);

        if (motivoExistente.isPresent()) {
            MotivoCita motivo = motivoExistente.get();
            motivo.setActivo(true);
            MotivoCita motivoActivado = motivoRepository.save(motivo);
            return Optional.of(MotivoCitaRes.fromEntity(motivoActivado));
        }

        return Optional.empty();
    }
}