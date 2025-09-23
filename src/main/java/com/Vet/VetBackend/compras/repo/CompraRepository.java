package com.Vet.VetBackend.compras.repo;

import com.Vet.VetBackend.compras.domain.Compra;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface CompraRepository extends JpaRepository<Compra, Long> {
    List<Compra> findByProveedorId(Integer proveedorId);
    List<Compra> findByFecha(LocalDate fecha);

    // List<Compra> findByUsuarioId(Integer usuarioId); // 🔜 Se activará tras migración
}

}
