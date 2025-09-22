package com.Vet.VetBackend.motivocitas.repo;

import com.Vet.VetBackend.motivocitas.domain.MotivoCita;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface MotivoCitaRepository extends JpaRepository<MotivoCita, Long> {
    List<MotivoCita> findByActivoTrue();
    Optional<MotivoCita> findByNombre(String nombre);
    boolean existsByNombreAndIdNot(String nombre, Long id);
}