package com.Vet.VetBackend.especie.app.services;



import com.Vet.VetBackend.especie.web.dto.EspecieActualizarReq;
import com.Vet.VetBackend.especie.web.dto.EspecieGuardarReq;
import com.Vet.VetBackend.especie.web.dto.EspecieRes;

import java.util.List;

public interface EspecieService {
    EspecieRes guardar(EspecieGuardarReq req);
    EspecieRes actualizar(EspecieActualizarReq req);
    void eliminar(Byte id);
    EspecieRes buscarPorId(Byte id);
    List<EspecieRes> listar();
}
