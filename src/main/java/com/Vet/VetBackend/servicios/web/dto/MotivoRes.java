package com.Vet.VetBackend.servicios.web.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MotivoRes {   // 👈 Asegúrate que tenga 'public'
    private Short id;
    private String nombre;
}
