package com.Vet.VetBackend.usuarios.app.services;

import com.Vet.VetBackend.usuarios.domain.Usuario;
import java.util.List;

public interface UsuarioService {
    Usuario crear(Usuario usuario);
    Usuario editar(Usuario usuario);
    void desactivar(Integer id);
    void activar(Integer id);
    Usuario obtenerPorId(Integer id);
    List<Usuario> listar();
    List<Usuario> listarActivos();
    List<Usuario> listarInactivos();
    Usuario obtenerPorNickName(String nickName);
}
