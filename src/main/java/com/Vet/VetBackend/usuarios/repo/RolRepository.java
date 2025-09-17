// src/main/java/com/Vet/VetBackend/usuarios/repo/RolRepository.java
package com.Vet.VetBackend.usuarios.repo;

import com.Vet.VetBackend.usuarios.domain.Rol;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RolRepository extends JpaRepository<Rol, Byte> {
    boolean existsByNombre(String nombre);
}
