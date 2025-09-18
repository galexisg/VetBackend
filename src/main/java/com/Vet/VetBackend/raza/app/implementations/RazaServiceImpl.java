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
    public RazaSalida crear(RazaGuardar razaGuardar) {
        Raza raza =razaRepository.save(modelMapper.map(razaGuardar, Raza.class));
        return modelMapper.map(raza, RazaSalida.class);
    }

    @Override
    public RazaSalida editar(RazaModificar razaModificar) {
        Raza raza =razaRepository.save(modelMapper.map(razaModificar, Raza.class));
        return modelMapper.map(raza, RazaSalida.class);
    }

    @Override
    public void eliminarPorId(Integer id) {
        razaRepository.deleteById(id);

    }
}
