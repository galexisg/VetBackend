// src/main/java/com/Vet/VetBackend/usuarios/repo/UsuarioRepository.java
package com.Vet.VetBackend.usuarios.repo;

import com.Vet.VetBackend.usuarios.domain.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {

    Optional<Usuario> findByCorreo(String correo);

    boolean existsByCorreo(String correo);

    boolean existsByNickName(String nickName);
}
