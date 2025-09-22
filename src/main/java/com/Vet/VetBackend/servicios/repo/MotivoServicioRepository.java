// src/main/java/com/Vet/VetBackend/servicios/repo/MotivoServicioRepository.java
package com.Vet.VetBackend.servicios.repo;

import com.Vet.VetBackend.servicios.domain.MotivoServicio;
import com.Vet.VetBackend.servicios.domain.Motivo;
import com.Vet.VetBackend.servicios.domain.Servicio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MotivoServicioRepository extends JpaRepository<MotivoServicio, Long> {
    boolean existsByMotivoIdAndServicioId(Short motivoId, Long servicioId);
    void deleteByMotivoIdAndServicioId(Short motivoId, Long servicioId);

    @Query("""
           select ms.servicio
           from MotivoServicio ms
           where ms.motivo.id = :motivoId
           """)
    List<Servicio> findServiciosByMotivoId(@Param("motivoId") Short motivoId);

    // Versión fija usando el enum 'estado' = ACTIVO
    @Query("""
           select ms.servicio
           from MotivoServicio ms
           where ms.motivo.id = :motivoId
             and ms.servicio.estado = com.Vet.VetBackend.servicios.domain.Servicio$EstadoServicio.ACTIVO
           """)
    List<Servicio> findServiciosActivosByMotivoId(@Param("motivoId") Short motivoId);

    @Query("""
           select ms.motivo
           from MotivoServicio ms
           where ms.servicio.id = :servicioId
           """)
    List<Motivo> findMotivosByServicioId(@Param("servicioId") Long servicioId);
}
