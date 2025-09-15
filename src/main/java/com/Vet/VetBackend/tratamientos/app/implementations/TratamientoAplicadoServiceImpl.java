package com.Vet.VetBackend.tratamientos.app.implementations;

import com.Vet.VetBackend.tratamientos.app.services.TratamientoAplicadoService;
import com.Vet.VetBackend.tratamientos.domain.TratamientoAplicado;
import com.Vet.VetBackend.tratamientos.repo.TratamientoAplicadoRepository;
import com.Vet.VetBackend.tratamientos.web.dto.TratamientoAplicadoReq;
import com.Vet.VetBackend.tratamientos.web.dto.TratamientoAplicadoRes;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
public class TratamientoAplicadoServiceImpl implements TratamientoAplicadoService {

    private final TratamientoAplicadoRepository repository;

    public TratamientoAplicadoServiceImpl(TratamientoAplicadoRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<TratamientoAplicadoRes> listarPorCita(Long citaId) {
        return repository.findByCitaId(citaId).stream()
                .map(TratamientoAplicadoRes::fromEntity).toList();
    }

    @Override
    public List<TratamientoAplicadoRes> listarPorHistorial(Long historialId) {
        return repository.findByHistorialId(historialId).stream()
                .map(TratamientoAplicadoRes::fromEntity).toList();
    }

    @Override
    public TratamientoAplicadoRes registrar(TratamientoAplicadoReq req) {
        TratamientoAplicado ta = new TratamientoAplicado();
        ta.setCitaId(req.getCitaId());
        ta.setTratamientoId(req.getTratamientoId());
        ta.setHistorialId(req.getHistorialId());
        ta.setVeterinarioId(req.getVeterinarioId());
        ta.setEstado("Planificado");
        ta.setObservaciones(req.getObservaciones());
        return TratamientoAplicadoRes.fromEntity(repository.save(ta));
    }

    @Override
    public TratamientoAplicadoRes actualizarEstado(Long id, String estado) {
        TratamientoAplicado ta = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tratamiento aplicado no encontrado"));
        ta.setEstado(estado);
        return TratamientoAplicadoRes.fromEntity(repository.save(ta));
    }

    @Override
    public TratamientoAplicadoRes agregarObservaciones(Long id, String observaciones) {
        TratamientoAplicado ta = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tratamiento aplicado no encontrado"));
        ta.setObservaciones(observaciones);
        return TratamientoAplicadoRes.fromEntity(repository.save(ta));
    }
}
