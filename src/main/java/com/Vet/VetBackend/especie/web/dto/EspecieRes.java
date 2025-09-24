package com.Vet.VetBackend.especie.web.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EspecieRes {
    private Byte especieId;
    private String nombre;
}
