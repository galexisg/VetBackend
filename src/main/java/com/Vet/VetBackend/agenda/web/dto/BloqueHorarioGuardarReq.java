package com.Vet.VetBackend.agenda.web.dto;

import lombok.*;
import java.time.LocalTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BloqueHorarioGuardarReq {
    private LocalTime inicio;
    private LocalTime fin;
}

