package com.Vet.VetBackend.consulta.repo;

import com.Vet.VetBackend.consulta.domain.ConsultaDiagnostico;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ConsultaDiagnosticoRepository extends JpaRepository <ConsultaDiagnostico, Long> {
    List<ConsultaDiagnostico> findByConsultaId(Long consultaId);
}
