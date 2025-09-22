package com.Vet.VetBackend.veterinario.app.services;

import com.Vet.VetBackend.veterinario.web.dto.EspecialidadGuardarReq;
import com.Vet.VetBackend.veterinario.web.dto.EspecialidadModificarReq;
import com.Vet.VetBackend.veterinario.web.dto.EspecialidadSalidaRes;

import java.util.List;

public interface IEspecialidadService {

    List<EspecialidadSalidaRes> listar();

    EspecialidadSalidaRes buscarPorId(Integer id);

    EspecialidadSalidaRes guardar(EspecialidadGuardarReq dto);

    EspecialidadSalidaRes modificar(EspecialidadModificarReq dto);

    void eliminar(Integer id);

    // nuevo método para activación lógica
    void activar(Integer id);

    List<EspecialidadSalidaRes> listarInactivos();

}