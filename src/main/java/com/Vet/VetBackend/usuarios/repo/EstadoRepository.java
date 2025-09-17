// src/main/java/com/Vet/VetBackend/usuarios/repo/EstadoRepository.java
package com.Vet.VetBackend.usuarios.repo;

import com.Vet.VetBackend.usuarios.domain.Estado;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EstadoRepository extends JpaRepository<Estado, Byte> { }