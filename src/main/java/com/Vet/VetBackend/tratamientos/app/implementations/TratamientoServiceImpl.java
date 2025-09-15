package com.Vet.VetBackend.tratamientos.app.implementations;

import com.Vet.VetBackend.tratamientos.app.services.TratamientoService;
import com.Vet.VetBackend.tratamientos.domain.Tratamiento;
import com.Vet.VetBackend.tratamientos.repo.TratamientoRepository;
import com.Vet.VetBackend.tratamientos.web.dto.TratamientoReq;
import com.Vet.VetBackend.tratamientos.web.dto.TratamientoRes;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TratamientoServiceImpl implements TratamientoService {

    private final TratamientoRepository repository;

    public TratamientoServiceImpl(TratamientoRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<TratamientoRes> listar() {
        return repository.findAll()
                .stream()
                .map(TratamientoRes::fromEntity)
                .toList();
    }

    @Override
    public TratamientoRes obtenerPorId(Long id) {
        return repository.findById(id)
                .map(TratamientoRes::fromEntity)
                .orElseThrow(() -> new RuntimeException("Tratamiento no encontrado"));
    }

    @Override
    public TratamientoRes crear(TratamientoReq req) {
        Tratamiento t = new Tratamiento();
        t.setNombre(req.getNombre() != null ? req.getNombre().trim() : null);
        t.setDescripcion(req.getDescripcion());
        t.setDuracionDias(req.getDuracionDias());
        t.setFrecuencia(req.getFrecuencia());
        t.setVia(req.getVia());
        t.setActivo(true);

        // NUEVO: precio sugerido
        t.setPrecioSugerido(req.getPrecioSugerido());

        return TratamientoRes.fromEntity(repository.save(t));
    }

    @Override
    public TratamientoRes actualizar(Long id, TratamientoReq req) {
        Tratamiento t = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tratamiento no encontrado"));

        if (req.getNombre() != null) t.setNombre(req.getNombre().trim());
        if (req.getDescripcion() != null) t.setDescripcion(req.getDescripcion());
        if (req.getDuracionDias() != null) t.setDuracionDias(req.getDuracionDias());
        if (req.getFrecuencia() != null) t.setFrecuencia(req.getFrecuencia());
        if (req.getVia() != null) t.setVia(req.getVia());

        // NUEVO: actualizar solo si llega (update parcial)
        if (req.getPrecioSugerido() != null) t.setPrecioSugerido(req.getPrecioSugerido());

        return TratamientoRes.fromEntity(repository.save(t));
    }

    @Override
    public void activarInactivar(Long id, boolean activo) {
        Tratamiento t = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tratamiento no encontrado"));
        t.setActivo(activo);
        repository.save(t);
    }
}
