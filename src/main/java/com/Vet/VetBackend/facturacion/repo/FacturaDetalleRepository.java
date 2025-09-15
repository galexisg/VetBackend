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

    // Buscar detalles por factura
    List<FacturaDetalle> findByFacturaId(Long facturaId);

    // Buscar detalles por servicio
    List<FacturaDetalle> findByServicioId(Long servicioId);

    // Buscar detalles por factura ordenados por ID
    List<FacturaDetalle> findByFacturaIdOrderById(Long facturaId);

    // Query personalizada: Detalles con información de factura
    @Query("SELECT fd FROM FacturaDetalle fd JOIN FETCH fd.factura WHERE fd.factura.id = :facturaId")
    List<FacturaDetalle> findByFacturaIdWithFactura(@Param("facturaId") Long facturaId);

    // Estadísticas: Servicios más vendidos
    @Query("SELECT fd.servicioId, SUM(fd.cantidad) as totalCantidad " +
            "FROM FacturaDetalle fd " +
            "JOIN fd.factura f " +
            "WHERE f.estado != 'ANULADA' " +
            "GROUP BY fd.servicioId " +
            "ORDER BY totalCantidad DESC")
    List<Object[]> getServiciosMasVendidos();

    // Estadísticas: Total vendido por servicio
    @Query("SELECT fd.servicioId, SUM(fd.subtotal) as totalVendido " +
            "FROM FacturaDetalle fd " +
            "JOIN fd.factura f " +
            "WHERE f.estado != 'ANULADA' " +
            "GROUP BY fd.servicioId " +
            "ORDER BY totalVendido DESC")
    List<Object[]> getTotalVendidoPorServicio();

    // Estadísticas: Ventas de un servicio en período
    @Query("SELECT SUM(fd.cantidad), SUM(fd.subtotal) " +
            "FROM FacturaDetalle fd " +
            "JOIN fd.factura f " +
            "WHERE fd.servicioId = :servicioId " +
            "AND f.createdAt BETWEEN :fechaInicio AND :fechaFin " +
            "AND f.estado != 'ANULADA'")
    Object[] getVentasServicioEnPeriodo(@Param("servicioId") Long servicioId,
                                        @Param("fechaInicio") LocalDateTime fechaInicio,
                                        @Param("fechaFin") LocalDateTime fechaFin);

    // Buscar detalles con descuento aplicado
    List<FacturaDetalle> findByDescuentoGreaterThan(BigDecimal descuento);

    // Query personalizada: Total de subtotales por factura (verificación)
    @Query("SELECT SUM(fd.subtotal) FROM FacturaDetalle fd WHERE fd.factura.id = :facturaId")
    BigDecimal getTotalSubtotalesPorFactura(@Param("facturaId") Long facturaId);

    // Buscar detalles por rango de precios
    @Query("SELECT fd FROM FacturaDetalle fd WHERE fd.precioUnitario BETWEEN :precioMin AND :precioMax")
    List<FacturaDetalle> findByRangoPrecios(@Param("precioMin") BigDecimal precioMin,
                                            @Param("precioMax") BigDecimal precioMax);

    // Contar detalles por servicio
    @Query("SELECT COUNT(fd) FROM FacturaDetalle fd WHERE fd.servicioId = :servicioId")
    Long countByServicioId(@Param("servicioId") Long servicioId);

    // Promedio de cantidad por servicio
    @Query("SELECT AVG(fd.cantidad) FROM FacturaDetalle fd WHERE fd.servicioId = :servicioId")
    Double getPromedioCantidadPorServicio(@Param("servicioId") Long servicioId);

    // Detalles con mayor subtotal
    @Query("SELECT fd FROM FacturaDetalle fd ORDER BY fd.subtotal DESC")
    List<FacturaDetalle> findTopDetallesPorSubtotal();

    // Eliminar detalles por factura (útil para actualizaciones)
    void deleteByFacturaId(Long facturaId);
}