package com.Vet.VetBackend.facturacion.web.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class FacturaDTO {

    private Long id;
    private Integer clienteId;          // <- INT → Integer
    private Long citaId;
    private String estado;
    private BigDecimal total;
    private BigDecimal saldo;
    private String motivoAnulacion;
    private Integer usuarioAnulaId;     // <- INT → Integer

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updatedAt;

    // extra
    private String nombreCliente;
    private String estadoDescripcion;
    private BigDecimal totalPagado;
    private Integer cantidadDetalles;
    private Integer cantidadPagos;

    private List<FacturaDetalleDTO> detalles;
    private List<PagoDTO> pagos;

    public FacturaDTO() {}

    public FacturaDTO(Long id, Integer clienteId, Long citaId, String estado,
                      BigDecimal total, BigDecimal saldo, LocalDateTime createdAt) {
        this.id = id;
        this.clienteId = clienteId;
        this.citaId = citaId;
        this.estado = estado;
        this.total = total;
        this.saldo = saldo;
        this.createdAt = createdAt;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Integer getClienteId() { return clienteId; }
    public void setClienteId(Integer clienteId) { this.clienteId = clienteId; }

    public Long getCitaId() { return citaId; }
    public void setCitaId(Long citaId) { this.citaId = citaId; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    public BigDecimal getTotal() { return total; }
    public void setTotal(BigDecimal total) { this.total = total; }

    public BigDecimal getSaldo() { return saldo; }
    public void setSaldo(BigDecimal saldo) { this.saldo = saldo; }

    public String getMotivoAnulacion() { return motivoAnulacion; }
    public void setMotivoAnulacion(String motivoAnulacion) { this.motivoAnulacion = motivoAnulacion; }

    public Integer getUsuarioAnulaId() { return usuarioAnulaId; }
    public void setUsuarioAnulaId(Integer usuarioAnulaId) { this.usuarioAnulaId = usuarioAnulaId; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }

    public String getNombreCliente() { return nombreCliente; }
    public void setNombreCliente(String nombreCliente) { this.nombreCliente = nombreCliente; }

    public String getEstadoDescripcion() { return estadoDescripcion; }
    public void setEstadoDescripcion(String estadoDescripcion) { this.estadoDescripcion = estadoDescripcion; }

    public BigDecimal getTotalPagado() { return totalPagado; }
    public void setTotalPagado(BigDecimal totalPagado) { this.totalPagado = totalPagado; }

    public Integer getCantidadDetalles() { return cantidadDetalles; }
    public void setCantidadDetalles(Integer cantidadDetalles) { this.cantidadDetalles = cantidadDetalles; }

    public Integer getCantidadPagos() { return cantidadPagos; }
    public void setCantidadPagos(Integer cantidadPagos) { this.cantidadPagos = cantidadPagos; }

    public List<FacturaDetalleDTO> getDetalles() { return detalles; }
    public void setDetalles(List<FacturaDetalleDTO> detalles) { this.detalles = detalles; }

    public List<PagoDTO> getPagos() { return pagos; }
    public void setPagos(List<PagoDTO> pagos) { this.pagos = pagos; }

    // Utilidad
    public boolean isPagada() {
        return saldo != null && saldo.compareTo(BigDecimal.ZERO) == 0;
    }
    public boolean isPendiente() { return "PENDIENTE".equalsIgnoreCase(estado); }
    public boolean isAnulada() { return "ANULADA".equalsIgnoreCase(estado); }

    public BigDecimal getPorcentajePagado() {
        if (total == null || total.compareTo(BigDecimal.ZERO) == 0) return BigDecimal.ZERO;
        if (totalPagado == null) return BigDecimal.ZERO;
        return totalPagado.divide(total, 4, BigDecimal.ROUND_HALF_UP)
                .multiply(BigDecimal.valueOf(100));
    }
}
