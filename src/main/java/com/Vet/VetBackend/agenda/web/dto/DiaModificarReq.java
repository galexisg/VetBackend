package com.Vet.VetBackend.agenda.web.dto;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DiaModificarReq {
    private String nombre;
    private int estadoDiaId;
}
