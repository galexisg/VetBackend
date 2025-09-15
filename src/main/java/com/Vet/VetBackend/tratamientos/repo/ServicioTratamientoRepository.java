package com.Vet.VetBackend.tratamientos.repo;


import com.Vet.VetBackend.tratamientos.domain.ServicioTratamiento;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ServicioTratamientoRepository extends JpaRepository<ServicioTratamiento, Long> {
    List<ServicioTratamiento> findByServicioId(Long servicioId);
    List<ServicioTratamiento> findByTratamientoId(Long tratamientoId);
}

