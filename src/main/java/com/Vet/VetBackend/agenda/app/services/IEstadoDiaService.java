package com.Vet.VetBackend.agenda.app.services;

import com.Vet.VetBackend.agenda.web.dto.EstadoDiaGuardarReq;
import com.Vet.VetBackend.agenda.web.dto.EstadoDiaModificarReq;
import com.Vet.VetBackend.agenda.web.dto.EstadoDiaSalidaRes;

import java.util.List;

public interface IEstadoDiaService {
    List<EstadoDiaSalidaRes> findAll();
    EstadoDiaSalidaRes findById(int estadoDiaId);
    EstadoDiaSalidaRes save(EstadoDiaGuardarReq estadoDiaDTO);
    EstadoDiaSalidaRes update(int estadoDiaId, EstadoDiaModificarReq estadoDiaDTO);
    void deleteById(int estadoDiaId);
}