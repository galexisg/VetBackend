package com.Vet.VetBackend.raza.app.implementations;


import com.Vet.VetBackend.raza.app.services.IRazaService;

import com.Vet.VetBackend.raza.domain.Raza;
import com.Vet.VetBackend.raza.repo.IRazaRepository;
import com.Vet.VetBackend.raza.web.dto.RazaGuardar;
import com.Vet.VetBackend.raza.web.dto.RazaModificar;
import com.Vet.VetBackend.raza.web.dto.RazaSalida;
import jakarta.persistence.Id;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RazaServiceImpl implements IRazaService{

    private static final Logger log = LoggerFactory.getLogger(RazaServiceImpl.class);
    @Autowired
    private IRazaRepository razaRepository;

    @Autowired
    private ModelMapper modelMapper;


    @Override
    public List<RazaSalida> obtenerTodos() {
        List<Raza> razas = razaRepository.findAll();
        return razas.stream()
                .map(raza -> modelMapper.map(raza,RazaSalida.class))
                .collect(Collectors.toList());
    }

    @Override
    public Page<RazaSalida> obtenerTodosPaginados(Pageable pageable) {
        Page<Raza> page = razaRepository.findAll(pageable);

        List<RazaSalida> razaDto = page.stream()
                .map(raza -> modelMapper.map(raza, RazaSalida.class))
                .collect(Collectors.toList());

        return new PageImpl<>(razaDto, page.getPageable(), page.getTotalElements());
    }

    @Override
    public RazaSalida obtenerPorId(Integer id) {
        return modelMapper.map(razaRepository.findById(id).get(), RazaSalida.class);
    }

    @Override
    public RazaSalida crear(RazaGuardar dto) {
        Raza r = new Raza();
        r.setNombre(dto.getNombre());
        r.setEspecieId(dto.getEspecieId());   // <-- ahora sí llega desde el DTO

        Raza saved = razaRepository.save(r);
        return toSalida(saved);               // <-- mapeo manual
    }

    @Override
    public RazaSalida editar(RazaModificar dto) {
        Raza raza = razaRepository.findById(dto.getId())
                .orElseThrow(() -> new RuntimeException("Raza no encontrada"));

        raza.setNombre(dto.getNombre());
        if (dto.getEspecieId() != null) {
            raza.setEspecieId(dto.getEspecieId());
        }

        Raza updated = razaRepository.save(raza);
        return toSalida(updated);
    }

    @Override
    public void eliminarPorId(Integer id) {
        razaRepository.deleteById(id);

    }

    private RazaSalida toSalida(Raza r) {
        RazaSalida dto = new RazaSalida();
        dto.setId(r.getId() != null ? r.getId().intValue() : null);  // convertir Byte → Integer
        dto.setNombre(r.getNombre());
        dto.setEspecieId(r.getEspecieId());
        return dto;
    }
}
