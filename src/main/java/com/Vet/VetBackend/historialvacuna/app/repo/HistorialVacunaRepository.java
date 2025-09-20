package com.Vet.VetBackend.historialvacuna.app.repo;

import com.Vet.VetBackend.historialvacuna.app.domain.HistorialVacuna;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HistorialVacunaRepository extends JpaRepository<HistorialVacuna, Integer> {

    List<HistorialVacuna> findByMascotaId(Integer mascotaId);

}