package com.Vet.VetBackend.almacen.app.implementations;

import com.Vet.VetBackend.almacen.domain.Almacen;
import com.Vet.VetBackend.almacen.repo.IAlmacenRepository;
import com.Vet.VetBackend.almacen.app.services.IAlmacenService;
import com.Vet.VetBackend.almacen.web.dto.Almacen_CambiarEstado;
import com.Vet.VetBackend.almacen.web.dto.Almacen_Guardar;
import com.Vet.VetBackend.almacen.web.dto.Almacen_Actualizar;
import com.Vet.VetBackend.almacen.web.dto.Almacen_Salida;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AlmacenService implements IAlmacenService {

    @Autowired
    private IAlmacenRepository repository;

    @Override
    public List<Almacen_Salida> obtenerTodos() {
        return repository.findAll().stream()
                .map(this::convertirASalida)
                .collect(Collectors.toList());
    }

    @Override
    public Page<Almacen_Salida> obtenerTodosPaginados(Pageable pageable) {
        Page<Almacen> page = repository.findAll(pageable);
        List<Almacen_Salida> dtoList = page.stream()
                .map(this::convertirASalida)
                .collect(Collectors.toList());
        return new PageImpl<>(dtoList, page.getPageable(), page.getTotalElements());
    }

    @Override
    public Almacen_Salida obtenerPorId(Integer id) {
        Almacen almacen = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Almacén no encontrado"));
        return convertirASalida(almacen);
    }

    @Override
    public Almacen_Salida crear(Almacen_Guardar dto) {
        Almacen almacen = new Almacen();
        almacen.setNombre(dto.getNombre());
        almacen.setUbicacion(dto.getUbicacion());
        almacen.setActivo(dto.getActivo());// o como lo manejes por defecto
        return convertirASalida(repository.save(almacen));
    }

    @Override
    public Almacen_Salida editar(Integer id, Almacen_Actualizar dto) {
        Almacen existente = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Almacén no encontrado"));
        existente.setNombre(dto.getNombre());
        existente.setUbicacion(dto.getUbicacion());
        return convertirASalida(repository.save(existente));
    }

    @Override
    public void eliminarPorId(Integer id) {
        repository.deleteById(id);
    }

    @Override
    public Almacen_Salida cambiarEstado(Integer id, Almacen_CambiarEstado dto) {
        Almacen almacen = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Almacén no encontrado"));
        almacen.setActivo(dto.getActivo());
        return convertirASalida(repository.save(almacen));
    }

    // 🔄 Métodos de conversión manual
    private Almacen_Salida convertirASalida(Almacen almacen) {
        Almacen_Salida salida = new Almacen_Salida();
        salida.setId(almacen.getId());
        salida.setNombre(almacen.getNombre());
        salida.setUbicacion(almacen.getUbicacion());
        salida.setActivo(almacen.getActivo());
        return salida;
    }
}
