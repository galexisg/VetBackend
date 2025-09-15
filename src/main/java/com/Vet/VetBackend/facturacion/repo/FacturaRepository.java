package com.Vet.VetBackend.facturacion.repo;

import com.Vet.VetBackend.facturacion.domain.Factura;
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

    // Buscar facturas por cliente
    List<Factura> findByClienteId(Long clienteId);

    // Buscar facturas por cita
    Optional<Factura> findByCitaId(Long citaId);

    // Buscar facturas por estado
    List<Factura> findByEstado(String estado);

    // Buscar facturas con saldo pendiente
    List<Factura> findBySaldoGreaterThan(BigDecimal saldo);

    // Buscar facturas por rango de fechas
    List<Factura> findByCreatedAtBetween(LocalDateTime fechaInicio, LocalDateTime fechaFin);

    // Buscar facturas por cliente y estado
    List<Factura> findByClienteIdAndEstado(Long clienteId, String estado);

    // Query personalizada: Facturas con detalles (evita N+1 queries)
    @Query("SELECT f FROM Factura f LEFT JOIN FETCH f.detalles WHERE f.id = :id")
    Optional<Factura> findByIdWithDetalles(@Param("id") Long id);

    // Query personalizada: Facturas con pagos
    @Query("SELECT f FROM Factura f LEFT JOIN FETCH f.pagos WHERE f.id = :id")
    Optional<Factura> findByIdWithPagos(@Param("id") Long id);

    // Query personalizada: Facturas completas (con detalles y pagos)
    @Query("SELECT DISTINCT f FROM Factura f " +
            "LEFT JOIN FETCH f.detalles " +
            "LEFT JOIN FETCH f.pagos " +
            "WHERE f.id = :id")
    Optional<Factura> findByIdComplete(@Param("id") Long id);

    // Estadísticas: Total facturado por cliente
    @Query("SELECT SUM(f.total) FROM Factura f WHERE f.clienteId = :clienteId AND f.estado != 'ANULADA'")
    BigDecimal getTotalFacturadoPorCliente(@Param("clienteId") Long clienteId);

    // Estadísticas: Facturas pendientes de pago
    @Query("SELECT f FROM Factura f WHERE f.saldo > 0 AND f.estado = 'PENDIENTE'")
    List<Factura> findFacturasPendientesPago();

    // Estadísticas: Total facturado en un período
    @Query("SELECT SUM(f.total) FROM Factura f WHERE f.createdAt BETWEEN :fechaInicio AND :fechaFin AND f.estado != 'ANULADA'")
    BigDecimal getTotalFacturadoEnPeriodo(@Param("fechaInicio") LocalDateTime fechaInicio,
                                          @Param("fechaFin") LocalDateTime fechaFin);

    // Buscar facturas por cliente en un período
    @Query("SELECT f FROM Factura f WHERE f.clienteId = :clienteId " +
            "AND f.createdAt BETWEEN :fechaInicio AND :fechaFin " +
            "ORDER BY f.createdAt DESC")
    List<Factura> findByClienteIdAndFechas(@Param("clienteId") Long clienteId,
                                           @Param("fechaInicio") LocalDateTime fechaInicio,
                                           @Param("fechaFin") LocalDateTime fechaFin);

    // Top facturas por monto
    @Query("SELECT f FROM Factura f WHERE f.estado != 'ANULADA' ORDER BY f.total DESC")
    List<Factura> findTopFacturasByMonto();

    // Contar facturas por estado
    @Query("SELECT COUNT(f) FROM Factura f WHERE f.estado = :estado")
    Long countByEstado(@Param("estado") String estado);

    // Buscar facturas que requieren seguimiento (con saldo mayor a X días)
    @Query("SELECT f FROM Factura f WHERE f.saldo > 0 " +
            "AND f.estado = 'PENDIENTE' " +
            "AND f.createdAt < :fechaLimite")
    List<Factura> findFacturasVencidas(@Param("fechaLimite") LocalDateTime fechaLimite);
}