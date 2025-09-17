package com.Vet.VetBackend.usuarios.app.implementations;

import com.Vet.VetBackend.usuarios.domain.Estado;
import com.Vet.VetBackend.usuarios.repo.EstadoRepository;
import com.Vet.VetBackend.usuarios.app.services.EstadoService;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class EstadoServiceImpl implements EstadoService {

    private final EstadoRepository estadoRepository;

    public EstadoServiceImpl(EstadoRepository estadoRepository) {
        this.estadoRepository = estadoRepository;
    }

    @Override
    public Estado obtenerPorId(Byte id) {
        return estadoRepository.findById(id).orElse(null);
    }

    @Override
    public List<Estado> listar() {
        return estadoRepository.findAll();
    }
}

