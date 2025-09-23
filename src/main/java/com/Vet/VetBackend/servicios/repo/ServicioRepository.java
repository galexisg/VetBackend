// src/main/java/com/Vet/VetBackend/servicios/repo/ServicioRepository.java
package com.Vet.VetBackend.servicios.repo;

import com.Vet.VetBackend.servicios.domain.Servicio;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface ServicioRepository extends JpaRepository<Servicio, Long>, JpaSpecificationExecutor<Servicio> {
    Optional<Servicio> findByNombreIgnoreCase(String nombre);
    // Elimina la línea Page<Servicio> findByActivo(Boolean activo, Pageable pageable);
}