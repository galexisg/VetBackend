package com.Vet.VetBackend.agenda.repo;

import com.Vet.VetBackend.agenda.domain.Dia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DiaRepository extends JpaRepository<Dia, Integer> {
}