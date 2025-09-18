package com.Vet.VetBackend.veterinario.app.implementations;

import com.Vet.VetBackend.veterinario.app.services.IVeterinarioService;
import com.Vet.VetBackend.veterinario.domain.Veterinario;
import com.Vet.VetBackend.veterinario.repo.VeterinarioRepository;
import com.Vet.VetBackend.veterinario.web.dto.VeterinarioGuardarReq;
import com.Vet.VetBackend.veterinario.web.dto.VeterinarioModificarReq;
import com.Vet.VetBackend.veterinario.web.dto.VeterinarioSalidaRes;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class VeterinarioServiceImpl implements IVeterinarioService {

    private final VeterinarioRepository repositorio;

    @Override
    public List<VeterinarioSalidaRes> listar() {
        return repositorio.findAll().stream()
                .map(this::toSalidaDTO)
                .toList();
    }

    @Override
    public List<VeterinarioSalidaRes> listarActivos() {
        return repositorio.findAll().stream()
                .filter(v -> v.getEstado() == Veterinario.Estado.Activo)
                .map(this::toSalidaDTO)
                .toList();
    }

    @Override
    public VeterinarioSalidaRes guardar(VeterinarioGuardarReq dto) {
        Veterinario veterinario = Veterinario.builder()
                .numeroLicencia(dto.getNumeroLicencia())
                .estado(Veterinario.Estado.Activo) // Activo por defecto
                .build();

        return toSalidaDTO(repositorio.save(veterinario));
    }

    @Override
    public VeterinarioSalidaRes modificar(VeterinarioModificarReq dto) {
        Veterinario veterinario = repositorio.findById(dto.getId())
                .orElseThrow(() -> new RuntimeException("Veterinario no encontrado"));

        veterinario.setNumeroLicencia(dto.getNumeroLicencia());
        if (dto.getEstado() != null) {
            veterinario.setEstado(Veterinario.Estado.valueOf(dto.getEstado()));
        }

        return toSalidaDTO(repositorio.save(veterinario));
    }

    @Override
    public void inactivar(int id) {
        Veterinario v = repositorio.findById(id)
                .orElseThrow(() -> new RuntimeException("Veterinario no encontrado"));
        v.setEstado(Veterinario.Estado.Inactivo);
        repositorio.save(v);
    }

    @Override
    public void activar(int id) {
        Veterinario v = repositorio.findById(id)
                .orElseThrow(() -> new RuntimeException("Veterinario no encontrado"));
        v.setEstado(Veterinario.Estado.Activo);
        repositorio.save(v);
    }

    // Mapeo de entidad a DTO de salida
    private VeterinarioSalidaRes toSalidaDTO(Veterinario v) {
        return VeterinarioSalidaRes.builder()
                .id(v.getId())
                .numeroLicencia(v.getNumeroLicencia())
                .estado(v.getEstado().name())
                .build();
    }
}
