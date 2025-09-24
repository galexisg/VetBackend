package com.Vet.VetBackend.diagnostico.repo;

import com.Vet.VetBackend.diagnostico.domain.Diagnostico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DiagnosticoRepository extends JpaRepository<Diagnostico, Long> {
    List<Diagnostico> findByCita_CitaId(Long citaId);
}
