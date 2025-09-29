package com.Vet.VetBackend.movimientoDetalle.repo;

import com.Vet.VetBackend.movimientoDetalle.domain.MovimientoDetalle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface IMovimientoDetalleRepository extends JpaRepository<MovimientoDetalle, Integer> {

    // Consultas existentes
    List<MovimientoDetalle> findByMovimientoInventario_Id(Integer id);
    List<MovimientoDetalle> findByMedicamento_Id(Integer id);
    List<MovimientoDetalle> findByLoteMedicamento_Id(Integer id);
    List<MovimientoDetalle> findByUsuario_Id(Integer id);

    // Eliminar en bloque todos los detalles asociados a un lote
    @Modifying
    @Transactional
    @Query("DELETE FROM MovimientoDetalle md WHERE md.loteMedicamento.id = :loteId")
    void deleteByLoteMedicamentoId(Integer loteId);

    // Saber si existen detalles vinculados a un lote
    boolean existsByLoteMedicamento_Id(Integer loteId);
}