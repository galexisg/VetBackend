/*package com.Vet.VetBackend.inventario.app.implementations;

import com.Vet.VetBackend.inventario.app.services.IInventarioService;
import com.Vet.VetBackend.inventario.repo.IInventarioRepository;
import com.Vet.VetBackend.inventario.domain.Inventario;
import com.Vet.VetBackend.inventario.web.dto.Inventario_Guardar;
import com.Vet.VetBackend.inventario.web.dto.Inventario_Modificar;
import com.Vet.VetBackend.inventario.web.dto.Inventario_Salida;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class InventarioService implements IInventarioService {
    @Autowired
    private IInventarioRepository inventarioRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public List<Inventario_Salida> obtenerTodos() {
        List<Inventario> inventarios = inventarioRepository.findAll();
        return inventarios.stream()
                .map(inventario -> modelMapper.map(inventario, Inventario_Salida.class))
                .collect(Collectors.toList());
    }

    @Override
    public Page<Inventario_Salida> obtenerTodosPaginados(Pageable pageable) {
        Page<Inventario> page = inventarioRepository.findAll(pageable);

        List<Inventario_Salida> inventariosDto = page.stream()
                .map(inventario -> modelMapper.map(inventario, Inventario_Salida.class))
                .collect(Collectors.toList());
        return new PageImpl<>(inventariosDto, page.getPageable(), page.getTotalElements());
    }

    @Override
    public Inventario_Salida obtenerPorId(Integer id) {
        Optional<Inventario> inventario = inventarioRepository.findById(id);

        if(inventario.isPresent()){
            return modelMapper.map(inventario.get(), Inventario_Salida.class);
        }
        return null;
    }

    @Override
    public List<Inventario_Salida> obtenerPorAlmacenId(Integer id) {
        List<Inventario> inventarios = inventarioRepository.findByAlmacenId(id);
        return inventarios.stream()
                .map(inventario -> modelMapper.map(inventario, Inventario_Salida.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<Inventario_Salida> obtenerPorMedicamentoId(Integer id) {
        List<Inventario> inventarios = inventarioRepository.findByMedicamentoId(id);
        return inventarios.stream()
                .map(inventario -> modelMapper.map(inventario, Inventario_Salida.class))
                .collect(Collectors.toList());
    }

    @Override
    public Inventario_Salida crear(Inventario_Guardar inventarioGuardar) {
        Inventario inventario = modelMapper.map(inventarioGuardar, Inventario.class);
        inventario.setId(null);

        return modelMapper.map(inventarioRepository.save(inventario), Inventario_Salida.class);
    }

    @Override
    public Inventario_Salida editar(Inventario_Modificar inventarioModificar) {
        Inventario inventario = inventarioRepository.save(modelMapper.map(inventarioModificar, Inventario.class));
        return modelMapper.map(inventario, Inventario_Salida.class);
    }

    @Override
    public void eliminarPorId(Integer id) {
        inventarioRepository.deleteById(id);
    }
}*/