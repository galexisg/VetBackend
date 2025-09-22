package com.Vet.VetBackend.agenda.web.dto;

import com.Vet.VetBackend.agenda.domain.EstadoDia;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EstadoDiaSalidaRes {

    private Integer estadoDiaId;

    private EstadoDia.Estado estado;
}