package com.Vet.VetBackend.proveedores.repo;

import com.Vet.VetBackend.proveedores.domain.Proveedor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IProveedorRepository extends JpaRepository<Proveedor, Integer> {
}
