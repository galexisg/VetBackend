// src/main/java/com/Vet/VetBackend/servicios/repo/MotivoServicioRepository.java
package com.Vet.VetBackend.servicios.repo;

import com.Vet.VetBackend.servicios.domain.MotivoServicio;
import com.Vet.VetBackend.servicios.domain.Motivo;
import com.Vet.VetBackend.servicios.domain.Servicio;
import com.Vet.VetBackend.servicios.domain.EstadoServicio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

public interface MotivoServicioRepository extends JpaRepository<MotivoServicio, Long> {
    boolean existsByMotivoIdAndServicioId(Short motivoId, Long servicioId);
    void deleteByMotivoIdAndServicioId(Short motivoId, Long servicioId);

    @Query("select ms.servicio from MotivoServicio ms where ms.motivo.id=:motivoId")
    List<Servicio> findServiciosByMotivoId(Short motivoId);

    @Query("select ms.servicio from MotivoServicio ms where ms.motivo.id=:motivoId and ms.servicio.estado = 'ACTIVO'")
    List<Servicio> findServiciosActivosByMotivoId(Short motivoId);

    @Query("select ms.motivo from MotivoServicio ms where ms.servicio.id=:servicioId")
    List<Motivo> findMotivosByServicioId(Long servicioId);
}