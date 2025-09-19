package com.Vet.VetBackend.consulta.repo;

import com.Vet.VetBackend.consulta.domain.Consulta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ConsultaRepository extends JpaRepository <Consulta, Long>{
     @Query("SELECT DISTINCT c FROM Consulta c " +
             "JOIN FETCH c.historial h " +
             "LEFT JOIN FETCH c.consultaDiagnosticos cd " +
             "LEFT JOIN FETCH cd.diagnostico " +
             "WHERE c.id = :id")
     Consulta findByIdWithDiagnosticos(@Param("id") Long id);

     @Query("SELECT DISTINCT c FROM Consulta c " +
             "JOIN FETCH c.historial h " +
             "LEFT JOIN FETCH c.consultaDiagnosticos cd " +
             "LEFT JOIN FETCH cd.diagnostico " +
             "WHERE h.id = :historialId")
     List<Consulta> findByHistorialIdWithDiagnosticos(@Param("historialId") Long historialId);

     @Query("SELECT DISTINCT c FROM Consulta c " +
             "JOIN FETCH c.historial h " +
             "LEFT JOIN FETCH c.consultaDiagnosticos cd " +
             "LEFT JOIN FETCH cd.diagnostico")
     List<Consulta> findAllWithDiagnosticos();
}
