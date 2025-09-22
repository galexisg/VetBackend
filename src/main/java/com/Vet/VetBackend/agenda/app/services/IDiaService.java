package com.Vet.VetBackend.agenda.app.services;

import com.Vet.VetBackend.agenda.web.dto.DiaGuardarReq;
import com.Vet.VetBackend.agenda.web.dto.DiaModificarReq;
import com.Vet.VetBackend.agenda.web.dto.DiaSalidaRes;

import java.util.List;

public interface IDiaService {
    List<DiaSalidaRes> findAll();
    DiaSalidaRes findById(int diaId);
    DiaSalidaRes save(DiaGuardarReq diaDTO);
    DiaSalidaRes update(int diaId, DiaModificarReq diaDTO);
    void deleteById(int diaId);
}