package com.Vet.VetBackend.compras.web.dto;

import lombok.Getter;
import lombok.Setter;
import java.io.Serializable;

@Getter
@Setter
public class CompraCancelar implements Serializable {
    private String motivoCancelacion;
}
