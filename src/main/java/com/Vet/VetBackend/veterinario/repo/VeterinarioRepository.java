package com.Vet.VetBackend.veterinario.repo;

import com.Vet.VetBackend.veterinario.domain.Veterinario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VeterinarioRepository extends JpaRepository<Veterinario, Integer> {

    // Filtra por estado
    List<Veterinario> findByEstado(Veterinario.Estado estado);

    // Filtra por especialidad (nota el guion bajo)
    List<Veterinario> findByEspecialidad_EspecialidadId(int especialidadId);

    // Filtra por servicio (nota el guion bajo)
    List<Veterinario> findByServicios_Id(Long id);

    // Filtra por estado y especialidad (nota el guion bajo)
    List<Veterinario> findByEstadoAndEspecialidad_EspecialidadId(Veterinario.Estado estado, int especialidadId);
}
