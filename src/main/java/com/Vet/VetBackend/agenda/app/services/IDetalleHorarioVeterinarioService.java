package com.Vet.VetBackend.agenda.app.services;
import com.Vet.VetBackend.agenda.web.dto.DetalleHorarioVeterinarioGuardarReq;
import com.Vet.VetBackend.agenda.web.dto.DetalleHorarioVeterinarioSalidaRes;
import java.util.List;

public interface IDetalleHorarioVeterinarioService {
    List<DetalleHorarioVeterinarioSalidaRes> findAll();
    DetalleHorarioVeterinarioSalidaRes findById(Integer id);
    DetalleHorarioVeterinarioSalidaRes save(DetalleHorarioVeterinarioGuardarReq detalleDTO);
    DetalleHorarioVeterinarioSalidaRes update(Integer id, DetalleHorarioVeterinarioGuardarReq detalleDTO);
    void deleteById(Integer id);
}
