package com.Vet.VetBackend.lote_medicamentos.repo;

import com.Vet.VetBackend.lote_medicamentos.domain.Lotes_medicamentos;
import org.springframework.data.jpa.repository.JpaRepository;

import java.sql.Date;
import java.util.List;

public interface ILotesMedicamentosRepository extends JpaRepository<Lotes_medicamentos, Integer> {

    List<Lotes_medicamentos> findByMedicamentoId(Integer medicamentoId);
    List<Lotes_medicamentos> findByProveedorId(Integer proveedorId);
    List<Lotes_medicamentos> findByFechaVencimientoBetween(Date inicio, Date fin);


}
