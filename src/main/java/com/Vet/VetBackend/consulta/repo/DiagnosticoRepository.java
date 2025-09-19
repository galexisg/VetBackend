package com.Vet.VetBackend.consulta.repo;

import com.Vet.VetBackend.consulta.domain.Diagnostico;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DiagnosticoRepository extends JpaRepository<Diagnostico, Long> {
}
