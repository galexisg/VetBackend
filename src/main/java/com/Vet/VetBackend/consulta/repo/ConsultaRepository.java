package com.Vet.VetBackend.consulta.repo;

import com.Vet.VetBackend.consulta.domain.Consulta;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ConsultaRepository extends JpaRepository <Consulta, Long>{
    List<Consulta> findByHistorialId(Long historialId);
}
