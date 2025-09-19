package com.Vet.VetBackend.consulta.repo;

import com.Vet.VetBackend.consulta.domain.ConsultaDiagnostico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ConsultaDiagnosticoRepository extends JpaRepository <ConsultaDiagnostico, Long> {
    @Query("SELECT cd FROM ConsultaDiagnostico cd " +
            "JOIN FETCH cd.diagnostico " +
            "WHERE cd.consulta.id = :consultaId")
    List<ConsultaDiagnostico> findWithDiagnosticoByConsultaId(@Param("consultaId") Long consultaId);
}
