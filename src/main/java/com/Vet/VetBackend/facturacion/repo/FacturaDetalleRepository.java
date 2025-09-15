package com.Vet.VetBackend.facturacion.repo;

import com.Vet.VetBackend.facturacion.domain.FacturaDetalle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface FacturaDetalleRepository extends JpaRepository<FacturaDetalle, Long> {

    // ===== Búsquedas base =====
    List<FacturaDetalle> findByFacturaId(Long facturaId);

    List<FacturaDetalle> findByServicioId(Long servicioId);

    // NUEVO: para tratamientos (útil a futuro)
    List<FacturaDetalle> findByTratamientoId(Long tratamientoId);

    List<FacturaDetalle> findByFacturaIdOrderById(Long facturaId);

    // NUEVO: contar detalles por factura (lo uso al eliminar para no borrar el único ítem)
    long countByFacturaId(Long facturaId);

    // ===== Fetch con factura =====
    @Query("SELECT fd FROM FacturaDetalle fd JOIN FETCH fd.factura WHERE fd.factura.id = :facturaId")
    List<FacturaDetalle> findByFacturaIdWithFactura(@Param("facturaId") Long facturaId);

    // ===== Estadísticas: SERVICIOS =====
    @Query("""
           SELECT fd.servicioId, SUM(fd.cantidad) as totalCantidad
           FROM FacturaDetalle fd
           JOIN fd.factura f
           WHERE f.estado <> 'ANULADA'
             AND fd.servicioId IS NOT NULL
           GROUP BY fd.servicioId
           ORDER BY totalCantidad DESC
           """)
    List<Object[]> getServiciosMasVendidos();

    @Query("""
           SELECT fd.servicioId, SUM(fd.subtotal) as totalVendido
           FROM FacturaDetalle fd
           JOIN fd.factura f
           WHERE f.estado <> 'ANULADA'
             AND fd.servicioId IS NOT NULL
           GROUP BY fd.servicioId
           ORDER BY totalVendido DESC
           """)
    List<Object[]> getTotalVendidoPorServicio();

    @Query("""
           SELECT SUM(fd.cantidad), SUM(fd.subtotal)
           FROM FacturaDetalle fd
           JOIN fd.factura f
           WHERE fd.servicioId = :servicioId
             AND f.createdAt BETWEEN :fechaInicio AND :fechaFin
             AND f.estado <> 'ANULADA'
           """)
    Object[] getVentasServicioEnPeriodo(@Param("servicioId") Long servicioId,
                                        @Param("fechaInicio") LocalDateTime fechaInicio,
                                        @Param("fechaFin") LocalDateTime fechaFin);

    // ===== Estadísticas: TRATAMIENTOS (opcional pero recomendable) =====
    @Query("""
           SELECT fd.tratamientoId, SUM(fd.cantidad) as totalCantidad
           FROM FacturaDetalle fd
           JOIN fd.factura f
           WHERE f.estado <> 'ANULADA'
             AND fd.tratamientoId IS NOT NULL
           GROUP BY fd.tratamientoId
           ORDER BY totalCantidad DESC
           """)
    List<Object[]> getTratamientosMasAplicados();

    @Query("""
           SELECT fd.tratamientoId, SUM(fd.subtotal) as totalVendido
           FROM FacturaDetalle fd
           JOIN fd.factura f
           WHERE f.estado <> 'ANULADA'
             AND fd.tratamientoId IS NOT NULL
           GROUP BY fd.tratamientoId
           ORDER BY totalVendido DESC
           """)
    List<Object[]> getTotalVendidoPorTratamiento();

    @Query("""
           SELECT SUM(fd.cantidad), SUM(fd.subtotal)
           FROM FacturaDetalle fd
           JOIN fd.factura f
           WHERE fd.tratamientoId = :tratamientoId
             AND f.createdAt BETWEEN :fechaInicio AND :fechaFin
             AND f.estado <> 'ANULADA'
           """)
    Object[] getVentasTratamientoEnPeriodo(@Param("tratamientoId") Long tratamientoId,
                                           @Param("fechaInicio") LocalDateTime fechaInicio,
                                           @Param("fechaFin") LocalDateTime fechaFin);

    // ===== Filtros varios =====
    List<FacturaDetalle> findByDescuentoGreaterThan(BigDecimal descuento);

    @Query("SELECT SUM(fd.subtotal) FROM FacturaDetalle fd WHERE fd.factura.id = :facturaId")
    BigDecimal getTotalSubtotalesPorFactura(@Param("facturaId") Long facturaId);

    @Query("SELECT fd FROM FacturaDetalle fd WHERE fd.precioUnitario BETWEEN :precioMin AND :precioMax")
    List<FacturaDetalle> findByRangoPrecios(@Param("precioMin") BigDecimal precioMin,
                                            @Param("precioMax") BigDecimal precioMax);

    @Query("SELECT COUNT(fd) FROM FacturaDetalle fd WHERE fd.servicioId = :servicioId")
    Long countByServicioId(@Param("servicioId") Long servicioId);

    @Query("SELECT AVG(fd.cantidad) FROM FacturaDetalle fd WHERE fd.servicioId = :servicioId")
    Double getPromedioCantidadPorServicio(@Param("servicioId") Long servicioId);

    @Query("SELECT fd FROM FacturaDetalle fd ORDER BY fd.subtotal DESC")
    List<FacturaDetalle> findTopDetallesPorSubtotal();

    void deleteByFacturaId(Long facturaId);
}
