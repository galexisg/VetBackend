package com.Vet.VetBackend.veterinario.repo;

import com.Vet.VetBackend.veterinario.domain.Veterinario;
import com.Vet.VetBackend.veterinario.web.dto.VeterinarioSalidaRes;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VeterinarioRepository extends JpaRepository<Veterinario, Integer> {

        // Filtra por estado
        List<Veterinario> findByEstado(Veterinario.Estado estado);
    }



