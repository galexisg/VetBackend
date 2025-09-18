
package com.Vet.VetBackend.estadocita.app.implementations;

import com.Vet.VetBackend.estadocita.app.services.IEstadoService;
import com.Vet.VetBackend.estadocita.domain.EstadoCita;
import com.Vet.VetBackend.estadocita.web.dto.EstadoGuardar;
import com.Vet.VetBackend.estadocita.web.dto.EstadoModificar;
import com.Vet.VetBackend.estadocita.web.dto.EstadoSalida;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.Vet.VetBackend.estadocita.repo.IEstadoRepository;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EstadoCitaServiceImpl implements IEstadoService{

    private static final Logger log = LoggerFactory.getLogger(EstadoCitaServiceImpl.class);
    @Autowired
    private IEstadoRepository estadoRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public List<EstadoSalida> obtenerTodos() {
        List<EstadoCita> estados = estadoRepository.findAll();
        return estados.stream()
                .map(estado -> modelMapper.map(estado,EstadoSalida.class))
                .collect(Collectors.toList());
    }

    @Override
    public Page<EstadoSalida> obtenerTodosPaginados(Pageable pageable) {
        Page<EstadoCita> page = estadoRepository.findAll(pageable);

        List<EstadoSalida> estadoDto = page.stream()
                .map(estado -> modelMapper.map(estado, EstadoSalida.class))
                .collect(Collectors.toList());

        return new PageImpl<>(estadoDto, page.getPageable(), page.getTotalElements());
    }

    @Override
    public EstadoSalida obtenerPorId(Integer id) {
        return modelMapper.map(estadoRepository.findById(id).get(), EstadoSalida.class);
    }

    @Override
    public EstadoSalida crear(EstadoGuardar estadoGuardar) {
        EstadoCita estado =estadoRepository.save(modelMapper.map(estadoGuardar, EstadoCita.class));
        return modelMapper.map(estado, EstadoSalida.class);
    }

    @Override
    public EstadoSalida editar(EstadoModificar estadoModificar) {
        EstadoCita estado =estadoRepository.save(modelMapper.map(estadoModificar, EstadoCita.class));
        return modelMapper.map(estado, EstadoSalida.class);
    }


}













