package com.Vet.VetBackend.compras.repo;

import com.Vet.VetBackend.compras.domain.Compra;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface CompraRepository extends JpaRepository<Compra, Long> {

    List<Compra> findByProveedor_Id(Integer proveedorId);

    List<Compra> findByFecha(LocalDate fecha);

    // Métodos que usan la propiedad 'id' de la entidad Usuario
    List<Compra> findByUsuario_Id(Integer usuarioId);
}
