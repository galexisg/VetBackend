package com.Vet.VetBackend.movimientoInventario.repo;

import com.Vet.VetBackend.movimientoInventario.domain.MovimientoInventario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IMovimientoInventarioRepository extends JpaRepository<MovimientoInventario, Integer> {
    List<MovimientoInventario> findByAlmacenId(Integer id);
    List<MovimientoInventario> findByUsuarioId(Integer id);;
}