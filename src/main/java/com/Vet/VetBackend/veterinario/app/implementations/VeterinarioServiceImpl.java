package com.Vet.VetBackend.veterinario.app.implementations;

import com.Vet.VetBackend.servicios.domain.Servicio;
import com.Vet.VetBackend.servicios.repo.ServicioRepository;
import com.Vet.VetBackend.usuarios.domain.Usuario;
import com.Vet.VetBackend.usuarios.repo.UsuarioRepository;
import com.Vet.VetBackend.veterinario.app.services.IVeterinarioService;
import com.Vet.VetBackend.veterinario.domain.Especialidad;
import com.Vet.VetBackend.veterinario.domain.Veterinario;
import com.Vet.VetBackend.veterinario.repo.EspecialidadRepository;
import com.Vet.VetBackend.veterinario.repo.VeterinarioRepository;
import com.Vet.VetBackend.veterinario.web.dto.VeterinarioGuardarReq;
import com.Vet.VetBackend.veterinario.web.dto.VeterinarioModificarReq;
import com.Vet.VetBackend.veterinario.web.dto.VeterinarioSalidaRes;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class VeterinarioServiceImpl implements IVeterinarioService {

    private final VeterinarioRepository repositorio;
    private final EspecialidadRepository especialidadRepo;
    private final UsuarioRepository usuarioRepo;
    private final ServicioRepository servicioRepo;

    @Override
    public VeterinarioSalidaRes guardar(VeterinarioGuardarReq dto) {
        Usuario usuario = usuarioRepo.findById(dto.getUsuarioId())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        Set<Especialidad> especialidades = especialidadRepo.findAllById(dto.getEspecialidadIds()).stream()
                .filter(Especialidad::getActivo)
                .collect(Collectors.toSet());

        Set<Servicio> servicios = servicioRepo.findAllById(dto.getServicioIds()).stream()
                .filter(Servicio::getActivo)
                .collect(Collectors.toSet());

        Veterinario veterinario = Veterinario.builder()
                .numeroLicencia(dto.getNumeroLicencia())
                .estado(Veterinario.Estado.Activo)
                .usuario(usuario)
                .especialidades(especialidades)
                .servicios(servicios)
                .build();

        return toSalidaDTO(repositorio.save(veterinario));
    }


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
    public List<VeterinarioSalidaRes> listarInactivos() {
        return repositorio.findAll().stream()
                .filter(v -> v.getEstado() == Veterinario.Estado.Inactivo)
                .map(this::toSalidaDTO)
                .toList();
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

    private VeterinarioSalidaRes toSalidaDTO(Veterinario v) {
        Set<String> nombresEspecialidades = v.getEspecialidades().stream()
                .map(Especialidad::getNombre)
                .collect(Collectors.toSet());

        Set<String> nombresServicios = v.getServicios().stream()
                .map(Servicio::getNombre)
                .collect(Collectors.toSet());

        return VeterinarioSalidaRes.builder()
                .id(v.getId())
                .numeroLicencia(v.getNumeroLicencia())
                .estado(v.getEstado().name())
                .especialidades(nombresEspecialidades)
                .servicios(nombresServicios)
                .build();
    }

}
