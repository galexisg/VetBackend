// src/main/java/com/Vet/VetBackend/veterinario/app/implementations/VeterinarioServiceImpl.java
package com.Vet.VetBackend.veterinario.app.implementations;

import com.Vet.VetBackend.servicios.domain.EstadoServicio;
import com.Vet.VetBackend.servicios.domain.Servicio;
import com.Vet.VetBackend.servicios.repo.ServicioRepository;
import com.Vet.VetBackend.usuarios.domain.Usuario;
import com.Vet.VetBackend.usuarios.repo.UsuarioRepository;
import com.Vet.VetBackend.veterinario.app.services.IVeterinarioService;
import com.Vet.VetBackend.veterinario.domain.Especialidad;
import com.Vet.VetBackend.veterinario.domain.Veterinario;
import com.Vet.VetBackend.veterinario.repo.EspecialidadRepository;
import com.Vet.VetBackend.veterinario.repo.VeterinarioRepository;
import com.Vet.VetBackend.veterinario.web.dto.UsuarioSalidaRes;
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
    private final EspecialidadRepository especialidadRepo;
    private final UsuarioRepository usuarioRepo;
    private final ServicioRepository servicioRepo;

    @Override
    public VeterinarioSalidaRes guardar(VeterinarioGuardarReq dto) {
        Usuario usuario = usuarioRepo.findById(dto.getUsuarioId())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        Servicio servicio = servicioRepo.findById(dto.getServicioId())
                .filter(s -> s.getEstado() == EstadoServicio.ACTIVO) // Corregido: Usa el enum
                .orElseThrow(() -> new RuntimeException("Servicio no encontrado o inactivo"));

        Especialidad especialidad = especialidadRepo.findById(dto.getEspecialidadId())
                .orElseThrow(() -> new RuntimeException("Especialidad no encontrada"));

        Veterinario veterinario = Veterinario.builder()
                .numeroLicencia(dto.getNumeroLicencia())
                .estado(Veterinario.Estado.Activo)
                .usuario(usuario)
                .servicios(servicio)
                .especialidad(especialidad)
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

        if (dto.getUsuarioId() != null) {
            Usuario usuario = usuarioRepo.findById(dto.getUsuarioId())
                    .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
            veterinario.setUsuario(usuario);
        }

        if (dto.getServicioId() != null) {
            Servicio servicio = servicioRepo.findById(dto.getServicioId())
                    .filter(s -> s.getEstado() == EstadoServicio.ACTIVO) // Corregido: Usa el enum
                    .orElseThrow(() -> new RuntimeException("Servicio no encontrado o inactivo"));
            veterinario.setServicios(servicio);
        }

        if (dto.getEspecialidadId() != null) {
            Especialidad especialidad = especialidadRepo.findById(dto.getEspecialidadId())
                    .orElseThrow(() -> new RuntimeException("Especialidad no encontrada"));
            veterinario.setEspecialidad(especialidad);
        }

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
        Usuario u = v.getUsuario();

        // Mapeo del usuario
        var usuarioDTO = UsuarioSalidaRes.builder()
                .id(u.getId())
                .nickName(u.getNickName())
                .correo(u.getCorreo())
                .nombreCompleto(u.getNombreCompleto())
                .dui(u.getDui())
                .telefono(u.getTelefono())
                .direccion(u.getDireccion())
                .fechaNacimiento(u.getFechaNacimiento())
                .rol(u.getRol().getNombre())       // Asumo que en Rol tienes un campo "nombre"
                .estado(u.getEstado().getNombre()) // Asumo que en Estado tienes un campo "nombre"
                .build();

        // Retorno con veterinario
        return VeterinarioSalidaRes.builder()
                .id(v.getId())
                .numeroLicencia(v.getNumeroLicencia())
                .estado(v.getEstado().name())
                .servicio(v.getServicios().getNombre())
                .especialidad(v.getEspecialidad().getNombre())
                .usuario(usuarioDTO) // 🔹 ahora devuelve todo el usuario
                .build();
    }

}