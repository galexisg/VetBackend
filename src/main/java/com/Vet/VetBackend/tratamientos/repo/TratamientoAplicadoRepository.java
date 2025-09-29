package com.Vet.VetBackend.tratamientos.repo;


import com.Vet.VetBackend.tratamientos.domain.TratamientoAplicado;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TratamientoAplicadoRepository extends JpaRepository<TratamientoAplicado, Long> {
    List<TratamientoAplicado> findByCitaId(Long citaId);
}


