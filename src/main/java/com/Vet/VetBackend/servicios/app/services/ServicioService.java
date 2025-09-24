// src/main/java/com/Vet/VetBackend/servicios/app/services/ServicioService.java
package com.Vet.VetBackend.servicios.app.services;

import com.Vet.VetBackend.servicios.web.dto.ServicioReq;
import com.Vet.VetBackend.servicios.web.dto.ServicioRes;
import com.Vet.VetBackend.servicios.web.dto.ServicioEstadoReq; // Añadir esta importación
import org.springframework.data.domain.Page;

public interface ServicioService {
    ServicioRes crear(ServicioReq req);
    ServicioRes actualizar(Long id, ServicioReq req);
    ServicioRes obtener(Long id);
    Page<ServicioRes> listar(String q, Boolean activo, int page, int size);

    // Método para cambiar el estado
    ServicioRes cambiarEstado(Long id, ServicioEstadoReq req);
}