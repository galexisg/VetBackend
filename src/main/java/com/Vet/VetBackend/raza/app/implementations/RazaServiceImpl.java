package com.Vet.VetBackend.raza.app.implementations;

import com.Vet.VetBackend.especie.domain.Especie;
import com.Vet.VetBackend.raza.app.services.IRazaService;
import com.Vet.VetBackend.raza.domain.Raza;
import com.Vet.VetBackend.raza.repo.IRazaRepository;
import com.Vet.VetBackend.raza.web.dto.RazaGuardar;
import com.Vet.VetBackend.raza.web.dto.RazaModificar;
import com.Vet.VetBackend.raza.web.dto.RazaSalida;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RazaServiceImpl implements IRazaService {

    private static final Logger log = LoggerFactory.getLogger(RazaServiceImpl.class);

    @Autowired
    private IRazaRepository razaRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public List<RazaSalida> obtenerTodos() {
        return razaRepository.findAll().stream()
                .map(this::toSalida)
                .collect(Collectors.toList());
    }

    @Override
    public Page<RazaSalida> obtenerTodosPaginados(Pageable pageable) {
        Page<Raza> page = razaRepository.findAll(pageable);
        List<RazaSalida> razaDto = page.getContent().stream()
                .map(this::toSalida)
                .collect(Collectors.toList());
        return new PageImpl<>(razaDto, pageable, page.getTotalElements());
    }

    @Override
    public RazaSalida obtenerPorId(Byte id) {
        Optional<Raza> optionalRaza = razaRepository.findById(id);
        if (optionalRaza.isEmpty()) {
            throw new RuntimeException("Raza no encontrada con id: " + id);
        }
        return toSalida(optionalRaza.get());
    }

    @Override
    public RazaSalida crear(RazaGuardar dto) {
        Raza r = Raza.builder()
                .nombre(dto.getNombre())
                .especie(Especie.builder().especieId(dto.getEspecieId()).build())
                .build();
        return toSalida(razaRepository.save(r));
    }

    @Override
    public RazaSalida editar(RazaModificar dto) {
        Raza raza = razaRepository.findById(dto.getId())
                .orElseThrow(() -> new RuntimeException("Raza no encontrada con id: " + dto.getId()));

        raza.setNombre(dto.getNombre());
        if (dto.getEspecieId() != null) {
            raza.setEspecie(Especie.builder().especieId(dto.getEspecieId()).build());
        }

        return toSalida(razaRepository.save(raza));
    }
    @Override
    public void eliminarPorId(Byte id) {
        if (!razaRepository.existsById(id)) {
            throw new RuntimeException("Raza no encontrada con id: " + id);
        }
        razaRepository.deleteById(id);
    }

    private RazaSalida toSalida(Raza r) {
        RazaSalida dto = new RazaSalida();
        dto.setId(r.getId());
        dto.setNombre(r.getNombre());
        dto.setEspecieId(r.getEspecie() != null ? r.getEspecie().getEspecieId() : null);
        return dto;
    }
}
