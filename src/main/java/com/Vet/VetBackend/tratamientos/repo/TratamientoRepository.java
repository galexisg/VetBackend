package com.Vet.VetBackend.tratamientos.repo;



import com.Vet.VetBackend.tratamientos.domain.Tratamiento;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TratamientoRepository extends JpaRepository<Tratamiento, Long> {
    Optional<Tratamiento> findByNombre(String nombre);
}


