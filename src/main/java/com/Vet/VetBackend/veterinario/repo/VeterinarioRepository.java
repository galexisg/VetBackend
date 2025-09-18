package com.Vet.VetBackend.veterinario.repo;

import com.Vet.VetBackend.veterinario.domain.Veterinario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VeterinarioRepository extends JpaRepository<Veterinario, Integer> {
    List<Veterinario> findByEstadoId(int estadoId);
}

