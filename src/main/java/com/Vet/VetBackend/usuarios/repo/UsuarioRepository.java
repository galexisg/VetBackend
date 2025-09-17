package com.Vet.VetBackend.usuarios.repo;

import com.Vet.VetBackend.usuarios.domain.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer> { }