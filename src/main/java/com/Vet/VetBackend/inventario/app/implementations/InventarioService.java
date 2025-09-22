package com.Vet.VetBackend.inventario.app.implementations;

import com.Vet.VetBackend.inventario.app.services.IInventarioService;
import com.Vet.VetBackend.inventario.repo.IInventarioRepository;
import com.Vet.VetBackend.inventario.domain.Inventario;
import com.Vet.VetBackend.inventario.web.dto.Inventario_Guardar;
import com.Vet.VetBackend.inventario.web.dto.Inventario_Modificar;
import com.Vet.VetBackend.inventario.web.dto.Inventario_Salida;
import com.Vet.VetBackend.almacen.domain.Almacen;
import com.Vet.VetBackend.Medicamento.domain.Medicamento;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class InventarioService implements IInventarioService {

    private final IInventarioRepository inventarioRepository;
    // private final IAlmacenRepository almacenRepository;
    // private final IMedicamentoRepository medicamentoRepository;

    public InventarioService(IInventarioRepository inventarioRepository) {
        this.inventarioRepository = inventarioRepository;
    }

    private Inventario_Salida toDto(Inventario inventario) {
        Inventario_Salida dto = new Inventario_Salida();
        dto.setId(inventario.getId());
        dto.setStockActual(inventario.getStockActual());
        dto.setStockMinimo(inventario.getStockMinimo());
        dto.setStockMaximo(inventario.getStockMaximo());
        /*
        dto.setAlmacen(inventario.getAlmacen());
        dto.setMedicamento(inventario.getMedicamento());
        */
        return dto;
    }

    // Método para convertir de DTO de guardar a la entidad
    private Inventario toEntity(Inventario_Guardar dto) {
        Inventario inventario = new Inventario();
        inventario.setStockActual(dto.getStockActual());
        inventario.setStockMinimo(dto.getStockMinimo());
        inventario.setStockMaximo(dto.getStockMaximo());

        /*
        Almacen almacen = almacenRepository.findById(dto.getAlmacenId())
                                        .orElseThrow(() -> new RuntimeException("Almacen no encontrado"));
        inventario.setAlmacen(almacen);
        Medicamento medicamento = medicamentoRepository.findById(dto.getMedicamentoId())
                                            .orElseThrow(() -> new RuntimeException("Medicamento no encontrado"));
        inventario.setMedicamento(medicamento);
        */
        return inventario;
    }

    // Método para convertir de DTO de modificar a la entidad
    private Inventario toEntity(Inventario_Modificar dto) {
        Inventario inventario = new Inventario();
        inventario.setId(dto.getId());
        inventario.setStockActual(dto.getStockActual());
        inventario.setStockMinimo(dto.getStockMinimo());
        inventario.setStockMaximo(dto.getStockMaximo());

        /*
        Almacen almacen = almacenRepository.findById(dto.getAlmacenId())
                                        .orElseThrow(() -> new RuntimeException("Almacen no encontrado"));
        inventario.setAlmacen(almacen);
        Medicamento medicamento = medicamentoRepository.findById(dto.getMedicamentoId())
                                            .orElseThrow(() -> new RuntimeException("Medicamento no encontrado"));
        inventario.setMedicamento(medicamento);
        */
        return inventario;
    }

    @Override
    public List<Inventario_Salida> obtenerTodos() {
        return inventarioRepository.findAll().stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public Page<Inventario_Salida> obtenerTodosPaginados(Pageable pageable) {
        Page<Inventario> page = inventarioRepository.findAll(pageable);
        List<Inventario_Salida> inventariosDto = page.getContent().stream()
                .map(this::toDto)
                .collect(Collectors.toList());
        return new PageImpl<>(inventariosDto, page.getPageable(), page.getTotalElements());
    }

    @Override
    public Inventario_Salida obtenerPorId(Integer id) {
        return inventarioRepository.findById(id)
                .map(this::toDto)
                .orElse(null);
    }

    @Override
    public List<Inventario_Salida> obtenerPorAlmacenId(Integer id) {
        return inventarioRepository.findByAlmacenId(id).stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<Inventario_Salida> obtenerPorMedicamentoId(Integer id) {
        return inventarioRepository.findByMedicamentoId(id).stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public Inventario_Salida crear(Inventario_Guardar inventarioGuardar) {
        Inventario nuevoInventario = toEntity(inventarioGuardar);
        Inventario guardado = inventarioRepository.save(nuevoInventario);
        return toDto(guardado);
    }

    @Override
    public Inventario_Salida editar(Inventario_Modificar inventarioModificar) {
        Optional<Inventario> inventarioExistente = inventarioRepository.findById(inventarioModificar.getId());
        if (inventarioExistente.isPresent()) {
            Inventario inventario = inventarioExistente.get();
            inventario.setStockActual(inventarioModificar.getStockActual());
            inventario.setStockMinimo(inventarioModificar.getStockMinimo());
            inventario.setStockMaximo(inventarioModificar.getStockMaximo());

            /*
            Almacen almacen = almacenRepository.findById(inventarioModificar.getAlmacenId())
                                            .orElseThrow(() -> new RuntimeException("Almacen no encontrado"));
            inventario.setAlmacen(almacen);
            Medicamento medicamento = medicamentoRepository.findById(inventarioModificar.getMedicamentoId())
                                                .orElseThrow(() -> new RuntimeException("Medicamento no encontrado"));
            inventario.setMedicamento(medicamento);
            */

            Inventario actualizado = inventarioRepository.save(inventario);
            return toDto(actualizado);
        }
        return null;
    }

    @Override
    public void eliminarPorId(Integer id) {
        inventarioRepository.deleteById(id);
    }
}