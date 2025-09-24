package com.Vet.VetBackend.raza.repo;



import com.Vet.VetBackend.raza.domain.Raza;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IRazaRepository  extends JpaRepository<Raza, Byte>{
}