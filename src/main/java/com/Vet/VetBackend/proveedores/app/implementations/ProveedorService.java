package com.Vet.VetBackend.proveedores.app.implementations;

import com.Vet.VetBackend.proveedores.web.dto.ProveedorGuardar;
import com.Vet.VetBackend.proveedores.web.dto.ProveedorModificar;
import com.Vet.VetBackend.proveedores.web.dto.ProveedorSalida;
import com.Vet.VetBackend.proveedores.domain.Proveedor;
import com.Vet.VetBackend.proveedores.repo.IProveedorRepository;
import com.Vet.VetBackend.proveedores.app.services.IProveedorService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProveedorService implements IProveedorService {

    private static final Logger log = LoggerFactory.getLogger(ProveedorService.class);
    @Autowired
    private IProveedorRepository proveedorRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public List<ProveedorSalida> obtenerTodos() {
        List<Proveedor> proveedores = proveedorRepository.findAll();
        return proveedores.stream()
                .map(proveedor -> modelMapper.map(proveedor, ProveedorSalida.class))
                .collect(Collectors.toList());
    }

    @Override
    public Page<ProveedorSalida> obtenerTodosPaginados(Pageable pageable) {
        Page<Proveedor> page = proveedorRepository.findAll(pageable);

        List<ProveedorSalida> proveedorDto = page.stream()
                .map(proveedor -> modelMapper.map(proveedor, ProveedorSalida.class))
                .collect(Collectors.toList());

        return new PageImpl<>(proveedorDto, page.getPageable(), page.getTotalElements());
    }

    @Override
    public ProveedorSalida obtenerPorId(Integer id) {
        return modelMapper.map(proveedorRepository.findById(id).get(), ProveedorSalida.class);
    }

    @Override
    public ProveedorSalida crear(ProveedorGuardar proveedorGuardar) {
        Proveedor proveedor =proveedorRepository.save(modelMapper.map(proveedorGuardar, Proveedor.class));
        return modelMapper.map(proveedor, ProveedorSalida.class);
    }

    @Override
    public ProveedorSalida editar(ProveedorModificar proveedorModificar) {
        Proveedor proveedor =proveedorRepository.save(modelMapper.map(proveedorModificar, Proveedor.class));
        return modelMapper.map(proveedor, ProveedorSalida.class);
    }

    @Override
    public void eliminarPorId(Integer id) {
        proveedorRepository.deleteById(id);

    }
}

