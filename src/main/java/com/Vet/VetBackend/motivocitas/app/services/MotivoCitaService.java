package com.Vet.VetBackend.motivocitas.app.services;


import com.Vet.VetBackend.motivocitas.web.dto.MotivoCitaRes;
import com.Vet.VetBackend.motivocitas.web.dto.MotivoCitaReq;
import java.util.List;
import java.util.Optional;

public interface MotivoCitaService {
    List<MotivoCitaRes> listarActivos();
    MotivoCitaRes crear(MotivoCitaReq request);
    Optional<MotivoCitaRes> editar(Long id, MotivoCitaReq request);
    Optional<MotivoCitaRes> desactivar(Long id);
    Optional<MotivoCitaRes> activar(Long id);
}