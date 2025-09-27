package com.Vet.VetBackend.veterinario.app.implementations;

import com.Vet.VetBackend.veterinario.app.services.IEspecialidadService;
import com.Vet.VetBackend.veterinario.domain.Especialidad;
import com.Vet.VetBackend.veterinario.repo.EspecialidadRepository;
import com.Vet.VetBackend.veterinario.web.dto.EspecialidadGuardarReq;
import com.Vet.VetBackend.veterinario.web.dto.EspecialidadModificarReq;
import com.Vet.VetBackend.veterinario.web.dto.EspecialidadSalidaRes;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EspecialidadServiceImpl implements IEspecialidadService {

    private final EspecialidadRepository repositorio;

    @Override
    public List<EspecialidadSalidaRes> listar() {
        return repositorio.findByActivoTrue().stream() // 🔹 solo activos
                .map(this::toSalidaDTO)
                .toList();
    }

    @Override
    public List<EspecialidadSalidaRes> listarInactivos() {
        return repositorio.findByActivoFalse().stream() // solo inactivos
                .map(this::toSalidaDTO)
                .toList();
    }

    // 🔹 Nuevo método para listar todos (activos + inactivos)
    @Override
    public List<EspecialidadSalidaRes> listarTodos() {
        return repositorio.findAll().stream()
                .map(this::toSalidaDTO)
                .toList();
    }


//    @Override
//    public EspecialidadSalidaRes buscarPorId(Integer id) {
//        Especialidad especialidad = repositorio.findById(id)
//                .filter(Especialidad::getActivo) // 🔹 aseguramos que esté activo
//                .orElseThrow(() -> new RuntimeException("Especialidad no encontrada o inactiva"));
//        return toSalidaDTO(especialidad);
//    }

    //nuevo codigo para buscar activos y inactivos en frontend
    @Override
    public EspecialidadSalidaRes buscarPorId(Integer id) {
        Especialidad especialidad = repositorio.findById(id)
                .orElseThrow(() -> new RuntimeException("Especialidad no encontrada"));
        return toSalidaDTO(especialidad);
    }


    @Override
    public EspecialidadSalidaRes guardar(EspecialidadGuardarReq dto) {
        Especialidad especialidad = toEntity(dto);
        especialidad.setActivo(true); // 🔹 nuevo registro siempre activo
        return toSalidaDTO(repositorio.save(especialidad));
    }

    @Override
    public EspecialidadSalidaRes modificar(EspecialidadModificarReq dto) {
        Especialidad especialidad = repositorio.findById(dto.getEspecialidadId())
//                .filter(Especialidad::getActivo) // quitando esto para poder modificar activos e inactivos
                .orElseThrow(() -> new RuntimeException("Especialidad no encontrada o inactiva"));

        especialidad.setNombre(dto.getNombre());

        if (dto.getActivo() != null) {
            especialidad.setActivo(dto.getActivo());
        }

        return toSalidaDTO(repositorio.save(especialidad));
    }


    @Override
    public void eliminar(Integer id) {
        Especialidad especialidad = repositorio.findById(id)
                .orElseThrow(() -> new RuntimeException("Especialidad no encontrada"));

        especialidad.setActivo(false); // 🔹 en lugar de borrar, se inactiva
        repositorio.save(especialidad);
    }

    @Override
    public void activar(Integer id) {
        Especialidad especialidad = repositorio.findById(id)
                .orElseThrow(() -> new RuntimeException("Especialidad no encontrada"));

        especialidad.setActivo(true);
        repositorio.save(especialidad);
    }


    // ======================
    // 🔹 Mappers manuales
    // ======================
    private Especialidad toEntity(EspecialidadGuardarReq dto) {
        return Especialidad.builder()
                .nombre(dto.getNombre())
                .activo(true) // 🔹 al crear siempre activo
                .build();
    }

    private EspecialidadSalidaRes toSalidaDTO(Especialidad especialidad) {
        return EspecialidadSalidaRes.builder()
                .especialidadId(especialidad.getEspecialidadId())
                .nombre(especialidad.getNombre())
                .activo(especialidad.getActivo())
                .build();
    }
}