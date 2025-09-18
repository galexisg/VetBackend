package com.Vet.VetBackend.compras.web.dto;

import java.io.Serializable;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CompraCancelar implements Serializable {
    private String motivoCancelacion;
}