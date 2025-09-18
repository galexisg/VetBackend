package com.Vet.VetBackend.usuarios.repo;

import com.Vet.VetBackend.usuarios.domain.Rol;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RolRepository extends JpaRepository<Rol, Byte> { }

