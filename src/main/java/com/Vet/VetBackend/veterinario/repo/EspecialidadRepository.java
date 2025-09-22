package com.Vet.VetBackend.veterinario.repo;

import com.Vet.VetBackend.veterinario.domain.Especialidad;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EspecialidadRepository extends JpaRepository<Especialidad, Integer> {

    // trae solo activos
    List<Especialidad> findByActivoTrue();

    // trae solo inactivos
    List<Especialidad> findByActivoFalse();

    // trae según el valor que se pase
    List<Especialidad> findByActivo(boolean activo);
}