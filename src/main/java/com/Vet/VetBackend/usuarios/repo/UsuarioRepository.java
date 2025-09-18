package com.Vet.VetBackend.usuarios.repo;

import com.Vet.VetBackend.usuarios.domain.Usuario;
import com.Vet.VetBackend.usuarios.domain.Estado;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {
    Usuario findByNickName(String nickName);

    List<Usuario> findByEstadoNombreIgnoreCase(String estado);
}