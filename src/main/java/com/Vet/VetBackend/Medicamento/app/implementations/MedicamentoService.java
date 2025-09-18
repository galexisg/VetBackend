package com.Vet.VetBackend.Medicamento.app.implementations;

import com.Vet.VetBackend.Medicamento.web.dto.MedicamentoGuardar;
import com.Vet.VetBackend.Medicamento.web.dto.MedicamentoModificar;
import com.Vet.VetBackend.Medicamento.web.dto.MedicamentoSalida;
import com.Vet.VetBackend.Medicamento.domain.Medicamento;
import com.Vet.VetBackend.Medicamento.repo.IMedicamentoRepository;
import com.Vet.VetBackend.Medicamento.app.services.IMedicamentoService;
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
public class MedicamentoService implements IMedicamentoService {

    private static final Logger log = LoggerFactory.getLogger(MedicamentoService.class);
    @Autowired
    private IMedicamentoRepository medicamentoRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public List<MedicamentoSalida> obtenerTodos() {
        List<Medicamento> medicamentos = medicamentoRepository.findAll();
        return medicamentos.stream()
                .map(medicamento -> modelMapper.map(medicamento, MedicamentoSalida.class))
                .collect(Collectors.toList());
    }

    @Override
    public Page<MedicamentoSalida> obtenerTodosPaginados(Pageable pageable) {
        Page<Medicamento> page = medicamentoRepository.findAll(pageable);

        List<MedicamentoSalida> medicamentoDto = page.stream()
                .map(medicamento -> modelMapper.map(medicamento, MedicamentoSalida.class))
                .collect(Collectors.toList());

        return new PageImpl<>(medicamentoDto, page.getPageable(), page.getTotalElements());
    }

    @Override
    public MedicamentoSalida obtenerPorId(Integer id) {
        return modelMapper.map(medicamentoRepository.findById(id).get(), MedicamentoSalida.class);
    }

    @Override
    public MedicamentoSalida crear(MedicamentoGuardar medicamentoGuardar) {
        Medicamento medicamento =medicamentoRepository.save(modelMapper.map(medicamentoGuardar, Medicamento.class));
        return modelMapper.map(medicamento, MedicamentoSalida.class);
    }

    @Override
    public MedicamentoSalida editar(MedicamentoModificar medicamentoSalida) {
        Medicamento medicamento =medicamentoRepository.save(modelMapper.map(medicamentoSalida, Medicamento.class));
        return modelMapper.map(medicamento, MedicamentoSalida.class);
    }

    @Override
    public void eliminarPorId(Integer id) {
        medicamentoRepository.deleteById(id);

    }
}

