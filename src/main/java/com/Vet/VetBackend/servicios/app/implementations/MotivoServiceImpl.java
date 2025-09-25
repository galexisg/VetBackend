// src/main/java/com/Vet/VetBackend/servicios/app/MotivoServiceImpl.java
package com.Vet.VetBackend.servicios.app.implementations;

import com.Vet.VetBackend.servicios.app.services.MotivoService;
import com.Vet.VetBackend.servicios.domain.Motivo;
import com.Vet.VetBackend.servicios.domain.MotivoServicio;
import com.Vet.VetBackend.servicios.domain.Servicio;
import com.Vet.VetBackend.servicios.repo.MotivoRepository;
import com.Vet.VetBackend.servicios.repo.MotivoServicioRepository;
import com.Vet.VetBackend.servicios.repo.ServicioRepository;
import com.Vet.VetBackend.servicios.web.dto.MotivoReq;
import com.Vet.VetBackend.servicios.web.dto.MotivoRes;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
@Transactional
public class MotivoServiceImpl implements MotivoService {

    private final MotivoRepository motivoRepo;
    private final MotivoServicioRepository msRepo;
    private final ServicioRepository servicioRepo;

    public MotivoServiceImpl(MotivoRepository motivoRepo, MotivoServicioRepository msRepo, ServicioRepository servicioRepo) {
        this.motivoRepo = motivoRepo;
        this.msRepo = msRepo;
        this.servicioRepo = servicioRepo;
    }

    @Override
    public MotivoRes crear(MotivoReq req) {
        Motivo m = Motivo.builder().nombre(req.getNombre()).build();
        return map(motivoRepo.save(m));
    }


    @Override
    public MotivoRes actualizar(Short id, MotivoReq req) {
        Motivo m = motivoRepo.findById(id).orElseThrow(() -> new NoSuchElementException("no encontrado"));
        m.setNombre(req.getNombre());
        return map(motivoRepo.save(m));
    }

    @Override
    @Transactional(readOnly = true)
    public MotivoRes obtener(Short id) {
        return map(motivoRepo.findById(id).orElseThrow(() -> new NoSuchElementException("no encontrado")));
    }

    @Override
    @Transactional(readOnly = true)
    public List<MotivoRes> listar() {
        return motivoRepo.findAll().stream().map(this::map).collect(Collectors.toList());
    }

    @Override
    public void vincular(Short motivoId, Long servicioId) {
        if (msRepo.existsByMotivoIdAndServicioId(motivoId, servicioId)) return;
        Motivo m = motivoRepo.findById(motivoId).orElseThrow(() -> new NoSuchElementException("motivo no encontrado"));
        Servicio s = servicioRepo.findById(servicioId).orElseThrow(() -> new NoSuchElementException("servicio no encontrado"));
        msRepo.save(MotivoServicio.builder().motivo(m).servicio(s).build());
    }

    @Override
    public void desvincular(Short motivoId, Long servicioId) {
        msRepo.deleteByMotivoIdAndServicioId(motivoId, servicioId);
    }

    private MotivoRes map(Motivo m) { return new MotivoRes(m.getId(), m.getNombre()); }
}
