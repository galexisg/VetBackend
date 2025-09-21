package com.Vet.VetBackend.citas.repo;

import com.Vet.VetBackend.citas.domain.Cita;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CitaRepository extends JpaRepository<Cita, Long> {
}