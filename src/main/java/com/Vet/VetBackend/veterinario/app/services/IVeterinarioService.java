package com.Vet.VetBackend.veterinario.app.services;

import com.Vet.VetBackend.veterinario.web.dto.VeterinarioGuardarReq;
import com.Vet.VetBackend.veterinario.web.dto.VeterinarioModificarReq;
import com.Vet.VetBackend.veterinario.web.dto.VeterinarioSalidaRes;

import java.util.List;

public interface IVeterinarioService {

    List<VeterinarioSalidaRes> listar();

    List<VeterinarioSalidaRes> listarActivos();

    VeterinarioSalidaRes guardar(VeterinarioGuardarReq dto);

    VeterinarioSalidaRes modificar(VeterinarioModificarReq dto);

    void inactivar(int id);

    void activar(int id);
}
