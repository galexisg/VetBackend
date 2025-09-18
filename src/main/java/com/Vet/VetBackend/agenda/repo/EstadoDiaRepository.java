package com.Vet.VetBackend.agenda.repo;

import com.Vet.VetBackend.agenda.domain.EstadoDia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EstadoDiaRepository extends JpaRepository<EstadoDia, Integer> {
}