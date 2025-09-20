package com.Vet.VetBackend.estadocita.repo;

import com.Vet.VetBackend.estadocita.domain.EstadoCita;
import com.Vet.VetBackend.estadocita.domain.EstadoCitaEnum;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IEstadoRepository extends JpaRepository<EstadoCita, Integer> {

    // Buscar estado por nombre (enum)
    Optional<EstadoCita> findByNombre(EstadoCitaEnum nombre);

    // Verificar si ya existe un estado con ese nombre
    boolean existsByNombre(EstadoCitaEnum nombre);
}
