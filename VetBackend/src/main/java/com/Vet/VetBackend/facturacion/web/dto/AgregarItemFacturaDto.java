package com.Vet.VetBackend.facturacion.web.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public class AgregarItemFacturaDto {

    @NotBlank
    private String tipoItem; // "SERVICIO" o "TRATAMIENTO"

    @NotNull
    private Long itemId;

    @NotNull
    @Positive
    private Integer cantidad;

    @NotNull
    @DecimalMin("0.01")
    private BigDecimal precioUnitario;

    // opcional
    private BigDecimal descuento = BigDecimal.ZERO;

    // opcional: congelo el texto mostrado (p.ej. nombre del catálogo al momento de facturar)
    private String descripcionItem;

    // ---- getters / setters ----
    public String getTipoItem() { return tipoItem; }
    public void setTipoItem(String tipoItem) { this.tipoItem = tipoItem; }

    public Long getItemId() { return itemId; }
    public void setItemId(Long itemId) { this.itemId = itemId; }

    public Integer getCantidad() { return cantidad; }
    public void setCantidad(Integer cantidad) { this.cantidad = cantidad; }

    public BigDecimal getPrecioUnitario() { return precioUnitario; }
    public void setPrecioUnitario(BigDecimal precioUnitario) { this.precioUnitario = precioUnitario; }

    public BigDecimal getDescuento() { return descuento; }
    public void setDescuento(BigDecimal descuento) { this.descuento = descuento; }

    public String getDescripcionItem() { return descripcionItem; }
    public void setDescripcionItem(String descripcionItem) { this.descripcionItem = descripcionItem; }
}
