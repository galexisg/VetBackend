package com.Vet.VetBackend.vacuna.repo;

import com.Vet.VetBackend.vacuna.domain.Vacuna;
import org.springframework.data.jpa.repository.JpaRepository;


public interface VacunaRepository extends JpaRepository<Vacuna, Integer> {
    boolean existsByNombreIgnoreCase(String nombre);
}