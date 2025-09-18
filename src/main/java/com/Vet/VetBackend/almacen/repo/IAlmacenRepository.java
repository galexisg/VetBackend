package com.Vet.VetBackend.almacen.repo;

import com.Vet.VetBackend.almacen.domain.Almacen;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IAlmacenRepository extends JpaRepository<Almacen, Integer> {

}
