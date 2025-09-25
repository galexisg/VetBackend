package com.Vet.VetBackend.servicios.app.services;

import com.Vet.VetBackend.servicios.web.dto.ServicioReq;
import com.Vet.VetBackend.servicios.web.dto.ServicioRes;
import com.Vet.VetBackend.servicios.web.dto.ServicioEstadoReq;
import com.Vet.VetBackend.servicios.web.dto.MotivoRes;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ServicioService {
    ServicioRes crear(ServicioReq req);
    ServicioRes actualizar(Long id, ServicioReq req);
    ServicioRes obtener(Long id);
    Page<ServicioRes> listar(String q, Boolean activo, int page, int size);
    ServicioRes cambiarEstado(Long id, ServicioEstadoReq req);

    // ✅ Nuevo método para listar motivos asociados a un servicio
    List<MotivoRes> listarMotivosPorServicio(Long servicioId);
}
