package com.Vet.VetBackend.agenda.app.implementations;

import com.Vet.VetBackend.agenda.app.services.IBloqueHorarioService;
import com.Vet.VetBackend.agenda.domain.BloqueHorario;
import com.Vet.VetBackend.agenda.repo.BloqueHorarioRepository;
import com.Vet.VetBackend.agenda.web.dto.BloqueHorarioGuardarReq;
import com.Vet.VetBackend.agenda.web.dto.BloqueHorarioModificarReq;
import com.Vet.VetBackend.agenda.web.dto.BloqueHorarioSalidaRes;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BloqueHorarioServiceImpl implements IBloqueHorarioService {

    private final BloqueHorarioRepository repositorio;

    @Override
    public List<BloqueHorarioSalidaRes> listar() {
        return repositorio.findByActivoTrue().stream()
                .map(this::toSalidaDTO)
                .toList();
    }

    @Override
    public List<BloqueHorarioSalidaRes> listarInactivos() {
        return repositorio.findByActivoFalse().stream()
                .map(this::toSalidaDTO)
                .toList();
    }

    @Override
    public BloqueHorarioSalidaRes buscarPorId(Integer id) {
        BloqueHorario bloque = repositorio.findById(id)
                .filter(BloqueHorario::getActivo)
                .orElseThrow(() -> new RuntimeException("BloqueHorario no encontrado o inactivo"));

        return toSalidaDTO(bloque);
    }

    @Override
    public BloqueHorarioSalidaRes guardar(BloqueHorarioGuardarReq dto) {
        BloqueHorario bloque = BloqueHorario.builder()
                .inicio(dto.getInicio())
                .fin(dto.getFin())
                .activo(true)
                .build();

        return toSalidaDTO(repositorio.save(bloque));
    }

    @Override
    public BloqueHorarioSalidaRes modificar(BloqueHorarioModificarReq dto) {
        BloqueHorario bloque = repositorio.findById(dto.getId())
                .filter(BloqueHorario::getActivo)
                .orElseThrow(() -> new RuntimeException("BloqueHorario no encontrado o inactivo"));

        bloque.setInicio(dto.getInicio());
        bloque.setFin(dto.getFin());

        return toSalidaDTO(repositorio.save(bloque));
    }

    @Override
    public void eliminar(Integer id) {
        BloqueHorario bloque = repositorio.findById(id)
                .orElseThrow(() -> new RuntimeException("BloqueHorario no encontrado"));

        bloque.setActivo(false);
        repositorio.save(bloque);
    }

    @Override
    public void activar(Integer id) {
        BloqueHorario bloque = repositorio.findById(id)
                .orElseThrow(() -> new RuntimeException("BloqueHorario no encontrado"));

        bloque.setActivo(true);
        repositorio.save(bloque);
    }

    // ======================
    // 🔹 Mapper
    // ======================
    private BloqueHorarioSalidaRes toSalidaDTO(BloqueHorario b) {
        return BloqueHorarioSalidaRes.builder()
                .id(b.getBloqueHorarioId())
                .inicio(b.getInicio())
                .fin(b.getFin())
                .build();
    }
}