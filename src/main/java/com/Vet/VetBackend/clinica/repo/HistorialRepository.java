package com.Vet.VetBackend.clinica.repo;

import com.Vet.VetBackend.clinica.domain.Historial;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface HistorialRepository extends JpaRepository<Historial, Long> {

}
