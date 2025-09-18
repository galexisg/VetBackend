package com.Vet.VetBackend.usuarios.app.implementations;

import com.Vet.VetBackend.usuarios.domain.Rol;
import com.Vet.VetBackend.usuarios.repo.RolRepository;
import com.Vet.VetBackend.usuarios.app.services.RolService;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class RolServiceImpl implements RolService {

    private final RolRepository rolRepository;

    public RolServiceImpl(RolRepository rolRepository) {
        this.rolRepository = rolRepository;
    }

    @Override
    public Rol obtenerPorId(Byte id) {
        return rolRepository.findById(id).orElse(null);
    }

    @Override
    public List<Rol> listar() {
        return rolRepository.findAll();
    }
}
