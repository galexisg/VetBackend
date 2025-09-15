package com.Vet.VetBackend.facturacion.repo;

import com.Vet.VetBackend.facturacion.domain.Factura;
import com.Vet.VetBackend.facturacion.domain.FacturaEstado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface FacturaRepository extends JpaRepository<Factura, Long> {

    // ===== Derived queries =====
    List<Factura> findByClienteId(Long clienteId);

    Optional<Factura> findByCitaId(Long citaId);

    // ahora tipado a enum
    List<Factura> findByEstado(FacturaEstado estado);

    List<Factura> findBySaldoGreaterThan(BigDecimal saldo);

    List<Factura> findByCreatedAtBetween(LocalDateTime fechaInicio, LocalDateTime fechaFin);

    // ahora tipado a enum
    List<Factura> findByClienteIdAndEstado(Long clienteId, FacturaEstado estado);

    // ===== Fetch helpers =====
    @Query("SELECT f FROM Factura f LEFT JOIN FETCH f.detalles WHERE f.id = :id")
    Optional<Factura> findByIdWithDetalles(@Param("id") Long id);

    @Query("SELECT f FROM Factura f LEFT JOIN FETCH f.pagos WHERE f.id = :id")
    Optional<Factura> findByIdWithPagos(@Param("id") Long id);

    @Query("""
           SELECT DISTINCT f FROM Factura f
           LEFT JOIN FETCH f.detalles
           LEFT JOIN FETCH f.pagos
           WHERE f.id = :id
           """)
    Optional<Factura> findByIdComplete(@Param("id") Long id);

    // ===== KPIs / Reports =====
    @Query("""
           SELECT SUM(f.total)
           FROM Factura f
           WHERE f.clienteId = :clienteId
             AND f.estado <> com.Vet.VetBackend.facturacion.domain.FacturaEstado.ANULADA
           """)
    BigDecimal getTotalFacturadoPorCliente(@Param("clienteId") Long clienteId);

    @Query("""
           SELECT f
           FROM Factura f
           WHERE f.saldo > 0
             AND f.estado = com.Vet.VetBackend.facturacion.domain.FacturaEstado.PENDIENTE
           """)
    List<Factura> findFacturasPendientesPago();

    @Query("""
           SELECT SUM(f.total)
           FROM Factura f
           WHERE f.createdAt BETWEEN :fechaInicio AND :fechaFin
             AND f.estado <> com.Vet.VetBackend.facturacion.domain.FacturaEstado.ANULADA
           """)
    BigDecimal getTotalFacturadoEnPeriodo(@Param("fechaInicio") LocalDateTime fechaInicio,
                                          @Param("fechaFin") LocalDateTime fechaFin);

    @Query("""
           SELECT f
           FROM Factura f
           WHERE f.clienteId = :clienteId
             AND f.createdAt BETWEEN :fechaInicio AND :fechaFin
           ORDER BY f.createdAt DESC
           """)
    List<Factura> findByClienteIdAndFechas(@Param("clienteId") Long clienteId,
                                           @Param("fechaInicio") LocalDateTime fechaInicio,
                                           @Param("fechaFin") LocalDateTime fechaFin);

    @Query("""
           SELECT f
           FROM Factura f
           WHERE f.estado <> com.Vet.VetBackend.facturacion.domain.FacturaEstado.ANULADA
           ORDER BY f.total DESC
           """)
    List<Factura> findTopFacturasByMonto();

    // ahora uso derived query tipada (sin @Query)
    Long countByEstado(FacturaEstado estado);

    @Query("""
           SELECT f
           FROM Factura f
           WHERE f.saldo > 0
             AND f.estado = com.Vet.VetBackend.facturacion.domain.FacturaEstado.PENDIENTE
             AND f.createdAt < :fechaLimite
           """)
    List<Factura> findFacturasVencidas(@Param("fechaLimite") LocalDateTime fechaLimite);
}
