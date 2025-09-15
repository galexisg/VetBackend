// src/main/java/com/Vet/VetBackend/servicios/app/MotivoService.java
package com.Vet.VetBackend.servicios.app.services;

import com.Vet.VetBackend.servicios.web.dto.MotivoReq;
import com.Vet.VetBackend.servicios.web.dto.MotivoRes;

import java.util.List;

public interface MotivoService {
    MotivoRes crear(MotivoReq req);
    MotivoRes actualizar(Short id, MotivoReq req);
    MotivoRes obtener(Short id);
    List<MotivoRes> listar();
    void vincular(Short motivoId, Long servicioId);
    void desvincular(Short motivoId, Long servicioId);
}
