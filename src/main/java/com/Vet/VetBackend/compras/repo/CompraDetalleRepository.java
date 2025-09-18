package com.Vet.VetBackend.compras.repo;


import com.Vet.VetBackend.compras.domain.CompraDetalle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CompraDetalleRepository extends JpaRepository<CompraDetalle, Long> {

    List<CompraDetalle> findByCompraId(Long compraId);
}
