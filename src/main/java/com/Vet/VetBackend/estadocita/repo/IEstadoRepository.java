

package com.Vet.VetBackend.estadocita.repo;

import com.Vet.VetBackend.estadocita.domain.EstadoCita;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IEstadoRepository  extends JpaRepository<EstadoCita, Integer>{
}
