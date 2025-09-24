package com.Vet.VetBackend.especie.app.implementations;

import com.Vet.VetBackend.especie.app.services.EspecieService;
import com.Vet.VetBackend.especie.domain.Especie;
import com.Vet.VetBackend.especie.repo.EspecieRepository;
import com.Vet.VetBackend.especie.web.dto.EspecieActualizarReq;
import com.Vet.VetBackend.especie.web.dto.EspecieGuardarReq;
import com.Vet.VetBackend.especie.web.dto.EspecieRes;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EspecieServiceImpl implements EspecieService {

    private final EspecieRepository especieRepository;

    @Override
    public EspecieRes guardar(EspecieGuardarReq req) {
        Especie especie = Especie.builder()
                .nombre(req.getNombre())
                .build();
        especie = especieRepository.save(especie);
        return toRes(especie);
    }

    @Override
    public EspecieRes actualizar(EspecieActualizarReq req) {
        Especie especie = especieRepository.findById(req.getEspecieId())
                .orElseThrow(() -> new RuntimeException("Especie no encontrada"));

        especie.setNombre(req.getNombre());
        especie = especieRepository.save(especie);
        return toRes(especie);
    }

    @Override
    public void eliminar(Byte id) {
        if (!especieRepository.existsById(id)) {
            throw new RuntimeException("Especie no encontrada");
        }
        especieRepository.deleteById(id);
    }

    @Override
    public EspecieRes buscarPorId(Byte id) {
        Especie especie = especieRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Especie no encontrada"));
        return toRes(especie);
    }

    @Override
    public List<EspecieRes> listar() {
        return especieRepository.findAll()
                .stream()
                .map(this::toRes)
                .toList();
    }

    private EspecieRes toRes(Especie especie) {
        return EspecieRes.builder()
                .especieId(especie.getEspecieId())
                .nombre(especie.getNombre())
                .build();
    }
}

