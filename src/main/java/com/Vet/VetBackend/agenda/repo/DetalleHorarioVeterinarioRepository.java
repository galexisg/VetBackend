package com.Vet.VetBackend.agenda.repo;

import com.Vet.VetBackend.agenda.domain.DetalleHorarioVeterinario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DetalleHorarioVeterinarioRepository extends JpaRepository<DetalleHorarioVeterinario, Integer> {

}
