package com.Vet.VetBackend.Medicamento.repo;



import com.Vet.VetBackend.Medicamento.domain.Medicamento;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IMedicamentoRepository  extends JpaRepository<Medicamento, Integer>{
}


