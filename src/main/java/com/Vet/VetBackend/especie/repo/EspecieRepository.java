package com.Vet.VetBackend.especie.repo;


import com.Vet.VetBackend.especie.domain.Especie;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EspecieRepository extends JpaRepository<Especie, Byte> {
    Optional<Especie> findByNombre(String nombre);
    boolean existsByNombre(String nombre);
}
