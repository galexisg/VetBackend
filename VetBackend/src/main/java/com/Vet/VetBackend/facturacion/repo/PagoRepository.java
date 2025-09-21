package com.Vet.VetBackend.facturacion.repo;

import com.Vet.VetBackend.facturacion.domain.Pago;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface PagoRepository extends JpaRepository<Pago, Long> {

    // Buscar pagos por factura
    List<Pago> findByFacturaId(Long facturaId);

    // Buscar pagos por método
    List<Pago> findByMetodo(String metodo);

    // Buscar pagos por factura ordenados por fecha
    List<Pago> findByFacturaIdOrderByFechaPagoDesc(Long facturaId);

    // Buscar pagos en rango de fechas
    List<Pago> findByFechaPagoBetween(LocalDateTime fechaInicio, LocalDateTime fechaFin);

    // Buscar pagos por método en un período
    List<Pago> findByMetodoAndFechaPagoBetween(String metodo,
                                               LocalDateTime fechaInicio,
                                               LocalDateTime fechaFin);

    // Query personalizada: Pagos con información de factura
    @Query("SELECT p FROM Pago p JOIN FETCH p.factura WHERE p.factura.id = :facturaId")
    List<Pago> findByFacturaIdWithFactura(@Param("facturaId") Long facturaId);

    // Estadísticas: Total pagado por factura
    @Query("SELECT SUM(p.monto) FROM Pago p WHERE p.factura.id = :facturaId")
    BigDecimal getTotalPagadoPorFactura(@Param("facturaId") Long facturaId);

    // Estadísticas: Total recaudado por método de pago
    @Query("SELECT p.metodo, SUM(p.monto) as totalRecaudado " +
            "FROM Pago p " +
            "GROUP BY p.metodo " +
            "ORDER BY totalRecaudado DESC")
    List<Object[]> getTotalRecaudadoPorMetodo();

    // Estadísticas: Total recaudado en un período
    @Query("SELECT SUM(p.monto) FROM Pago p WHERE p.fechaPago BETWEEN :fechaInicio AND :fechaFin")
    BigDecimal getTotalRecaudadoEnPeriodo(@Param("fechaInicio") LocalDateTime fechaInicio,
                                          @Param("fechaFin") LocalDateTime fechaFin);

    // Estadísticas: Recaudación diaria
    @Query("SELECT DATE(p.fechaPago), SUM(p.monto) " +
            "FROM Pago p " +
            "WHERE p.fechaPago BETWEEN :fechaInicio AND :fechaFin " +
            "GROUP BY DATE(p.fechaPago) " +
            "ORDER BY DATE(p.fechaPago)")
    List<Object[]> getRecaudacionDiaria(@Param("fechaInicio") LocalDateTime fechaInicio,
                                        @Param("fechaFin") LocalDateTime fechaFin);

    // Buscar pagos por monto mínimo
    List<Pago> findByMontoGreaterThanEqual(BigDecimal montoMinimo);

    // Buscar pagos por rango de montos
    List<Pago> findByMontoBetween(BigDecimal montoMin, BigDecimal montoMax);

    // Query personalizada: Último pago de una factura
    @Query("SELECT p FROM Pago p WHERE p.factura.id = :facturaId ORDER BY p.fechaPago DESC LIMIT 1")
    Pago findUltimoPagoPorFactura(@Param("facturaId") Long facturaId);

    // Contar pagos por método
    @Query("SELECT COUNT(p) FROM Pago p WHERE p.metodo = :metodo")
    Long countByMetodo(@Param("metodo") String metodo);

    // Promedio de monto por método de pago
    @Query("SELECT AVG(p.monto) FROM Pago p WHERE p.metodo = :metodo")
    Double getPromedioMontoPorMetodo(@Param("metodo") String metodo);

    // Pagos realizados hoy
    @Query("SELECT p FROM Pago p WHERE DATE(p.fechaPago) = CURRENT_DATE")
    List<Pago> findPagosHoy();

    // Estadísticas: Top pagos por monto
    @Query("SELECT p FROM Pago p ORDER BY p.monto DESC")
    List<Pago> findTopPagosPorMonto();

    // Buscar pagos de un cliente específico
    @Query("SELECT p FROM Pago p JOIN p.factura f WHERE f.clienteId = :clienteId")
    List<Pago> findByClienteId(@Param("clienteId") Long clienteId);

    // Total pagado por un cliente
    @Query("SELECT SUM(p.monto) FROM Pago p JOIN p.factura f WHERE f.clienteId = :clienteId")
    BigDecimal getTotalPagadoPorCliente(@Param("clienteId") Long clienteId);

    // Pagos realizados en el último mes
    @Query("SELECT p FROM Pago p WHERE p.fechaPago >= :fechaInicio")
    List<Pago> findPagosUltimoMes(@Param("fechaInicio") LocalDateTime fechaInicio);
}