package com.Vet.VetBackend.estadocita.domain;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum EstadoCitaEnum {
    PENDIENTE,
    CONFIRMADA,
    CANCELADA,
    COMPLETADA;

    @JsonCreator
    public static EstadoCitaEnum fromString(String value) {
        return EstadoCitaEnum.valueOf(value.toUpperCase());
    }
}
