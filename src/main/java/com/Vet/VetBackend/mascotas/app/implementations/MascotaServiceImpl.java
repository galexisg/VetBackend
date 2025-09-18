package com.Vet.VetBackend.mascotas.app.implementations;

import com.Vet.VetBackend.mascotas.app.services.MascotaService;
import com.Vet.VetBackend.mascotas.domain.Mascota;
import com.Vet.VetBackend.mascotas.repo.MascotaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class MascotaServiceImpl implements MascotaService {

    private final MascotaRepository mascotaRepository;

    public MascotaServiceImpl(MascotaRepository mascotaRepository) {
        this.mascotaRepository = mascotaRepository;
    }

    @Override
    public Mascota crear(Mascota mascota) {
        return mascotaRepository.save(mascota);
    }

    @Override
    public Mascota actualizar(Integer id, Mascota mascota) {
        Mascota existente = mascotaRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Mascota no encontrada con id " + id));

        existente.setNombre(mascota.getNombre());
        existente.setFoto(mascota.getFoto());
        existente.setAlergia(mascota.getAlergia());
        existente.setNacimiento(mascota.getNacimiento());
        existente.setSexo(mascota.getSexo());
        existente.setPeso(mascota.getPeso());
       // existente.setUsuario(mascota.getUsuario());
//        existente.setRaza(mascota.getRaza());

        return mascotaRepository.save(existente);
    }

    @Override
    public Mascota obtenerPorId(Integer id) {
        return mascotaRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Mascota no encontrada con id " + id));
    }

    @Override
    public List<Mascota> listarTodos() {
        return mascotaRepository.findAll();
    }
}
