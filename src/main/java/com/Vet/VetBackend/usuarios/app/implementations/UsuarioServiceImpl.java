package com.Vet.VetBackend.usuarios.app.implementations;

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
        usuarioRepository.findById(id).ifPresent(u -> {
            u.getEstado().setId((byte)2); // desactivado
            usuarioRepository.save(u);
        });
    }

    @Override
    public void activar(Integer id) {
        usuarioRepository.findById(id).ifPresent(u -> {
            u.getEstado().setId((byte)1); // activo
            usuarioRepository.save(u);
        });
    }

    @Override
    public Usuario obtenerPorId(Integer id) {
        return usuarioRepository.findById(id).orElse(null);
    }

    @Override
    public List<Usuario> listar() {
        return usuarioRepository.findAll();
    }
}
