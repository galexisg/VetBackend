package com.Vet.VetBackend.vacuna.app.implementations;

import com.Vet.VetBackend.vacuna.domain.Vacuna;
import com.Vet.VetBackend.vacuna.web.exception.BadRequestException;
import com.Vet.VetBackend.vacuna.web.exception.NotFoundException;
import com.Vet.VetBackend.vacuna.repo.VacunaRepository;
import com.Vet.VetBackend.vacuna.app.services.VacunaService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class VacunaServiceImpl implements VacunaService {

    private final VacunaRepository repo;

    @Override
    public List<Vacuna> listar(String q) {
        var all = repo.findAll();
        if (q == null || q.isBlank()) return all;

        final String s = q.toLowerCase();
        return all.stream()
                .filter(v -> v.getNombre() != null && v.getNombre().toLowerCase().contains(s))
                .collect(Collectors.toList());
    }

    @Override
    public Vacuna obtener(Integer id) {
        return repo.findById(id)
                .orElseThrow(() -> new NotFoundException("Vacuna no encontrada"));
    }

    @Override
    public Vacuna crear(Vacuna v) {
        if (repo.existsByNombreIgnoreCase(v.getNombre())) {
            throw new BadRequestException("Ya existe una vacuna con ese nombre");
        }
        return repo.save(v);
    }

    @Override
    public Vacuna actualizar(Integer id, Vacuna v) {
        var actual = obtener(id);

        if (!actual.getNombre().equalsIgnoreCase(v.getNombre())
                && repo.existsByNombreIgnoreCase(v.getNombre())) {
            throw new BadRequestException("Nombre de vacuna ya utilizado");
        }

        actual.setVacunaId(id); // ⚠️ campo correcto de la entidad
        actual.setNombre(v.getNombre());

        return repo.save(actual);
    }

}

