package com.Vet.VetBackend.usuarios.app.implementations;

import com.Vet.VetBackend.usuarios.domain.Estado;
import com.Vet.VetBackend.usuarios.domain.Usuario;
import com.Vet.VetBackend.usuarios.repo.UsuarioRepository;
import com.Vet.VetBackend.usuarios.app.services.UsuarioService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Service
@Transactional
public class UsuarioServiceImpl implements UsuarioService {

    private final UsuarioRepository usuarioRepository;

    public UsuarioServiceImpl(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public Usuario crear(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    @Override
    public Usuario editar(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    @Override
    public void desactivar(Integer id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        Estado estadoInactivo = new Estado();
        estadoInactivo.setId((byte) 2); // ID de "inactivo" en tu BD
        usuario.setEstado(estadoInactivo);

        usuarioRepository.save(usuario);
    }

    @Override
    public void activar(Integer id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        Estado estadoActivo = new Estado();
        estadoActivo.setId((byte) 1); // ID de "activo" en tu BD
        usuario.setEstado(estadoActivo);

        usuarioRepository.save(usuario);
    }

    @Override
    public List<Usuario> listarActivos() {
        return usuarioRepository.findByEstadoNombreIgnoreCase("Activo");
    }

    @Override
    public List<Usuario> listarInactivos() {
        return usuarioRepository.findByEstadoNombreIgnoreCase("Inactivo");
    }

    @Override
    public Usuario obtenerPorId(Integer id) {
        return usuarioRepository.findById(id).orElse(null);
    }

    @Override
    public List<Usuario> listar() {
        return usuarioRepository.findAll();
    }

    @Override
    public Usuario obtenerPorNickName(String nickName) {
        return usuarioRepository.findByNickName(nickName);
    }
}
