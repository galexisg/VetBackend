package com.Vet.VetBackend.agenda.app.services;

import com.Vet.VetBackend.agenda.web.dto.BloqueHorarioGuardarReq;
import com.Vet.VetBackend.agenda.web.dto.BloqueHorarioModificarReq;
import com.Vet.VetBackend.agenda.web.dto.BloqueHorarioSalidaRes;

import java.util.List;

public interface IBloqueHorarioService {

    List<BloqueHorarioSalidaRes> listar();           // activos
    List<BloqueHorarioSalidaRes> listarInactivos();  // inactivos

    BloqueHorarioSalidaRes buscarPorId(Integer id);

    BloqueHorarioSalidaRes guardar(BloqueHorarioGuardarReq dto);

    BloqueHorarioSalidaRes modificar(BloqueHorarioModificarReq dto);

    void eliminar(Integer id); // 🔹 desactivar

    void activar(Integer id);  // 🔹 reactivar
}