package com.Vet.VetBackend.inventario.app.implementations;

import com.Vet.VetBackend.Medicamento.domain.Medicamento;
import com.Vet.VetBackend.Medicamento.repo.IMedicamentoRepository;
import com.Vet.VetBackend.Medicamento.web.dto.MedicamentoSalida;
import com.Vet.VetBackend.almacen.domain.Almacen;
import com.Vet.VetBackend.almacen.repo.IAlmacenRepository;
import com.Vet.VetBackend.almacen.web.dto.Almacen_Salida;
import com.Vet.VetBackend.inventario.app.services.IInventarioService;
import com.Vet.VetBackend.inventario.domain.Inventario;
import com.Vet.VetBackend.inventario.repo.IInventarioRepository;
import com.Vet.VetBackend.inventario.web.dto.Inventario_Guardar;
import com.Vet.VetBackend.inventario.web.dto.Inventario_Modificar;
import com.Vet.VetBackend.inventario.web.dto.Inventario_Salida;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class InventarioService implements IInventarioService {

    private final IInventarioRepository inventarioRepository;
    private final IAlmacenRepository almacenRepository;
    private final IMedicamentoRepository medicamentoRepository;

    public InventarioService(IInventarioRepository inventarioRepository,
                             IAlmacenRepository almacenRepository,
                             IMedicamentoRepository medicamentoRepository) {
        this.inventarioRepository = inventarioRepository;
        this.almacenRepository = almacenRepository;
        this.medicamentoRepository = medicamentoRepository;
    }

    // --- Mapeadores de Entidad a DTO Anidado (AUXILIARES) ---

    // Mapea la entidad Medicamento a su DTO de salida
    private MedicamentoSalida toMedicamentoSalida(Medicamento medicamento) {
        if (medicamento == null) return null;
        MedicamentoSalida dto = new MedicamentoSalida();
        dto.setId(medicamento.getId());
        dto.setNombre(medicamento.getNombre());
        dto.setFormafarmacautica(medicamento.getFormafarmacautica());
        dto.setConcentracion(medicamento.getConcentracion());
        dto.setUnidad(medicamento.getUnidad());
        dto.setVia(medicamento.getVia());
        dto.setRequiereReceta(medicamento.getRequiereReceta());
        dto.setActivo(medicamento.getActivo());
        dto.setTemperaturaalm(medicamento.getTemperaturaalm());
        dto.setVidautilmeses(medicamento.getVidautilmeses());
        return dto;
    }

    // Mapea la entidad Almacen a su DTO de salida
    private Almacen_Salida toAlmacenSalida(Almacen almacen) {
        if (almacen == null) return null;
        Almacen_Salida dto = new Almacen_Salida();
        dto.setId(almacen.getId());
        dto.setNombre(almacen.getNombre());
        dto.setUbicacion(almacen.getUbicacion());
        dto.setActivo(almacen.getActivo());
        return dto;
    }

    // Mapeador Principal (Inventario -> Inventario_Salida)
    private Inventario_Salida toDto(Inventario inventario) {
        if (inventario == null) return null;

        Inventario_Salida dto = new Inventario_Salida();
        dto.setId(inventario.getId());
        dto.setStockActual(inventario.getStockActual());
        dto.setStockMinimo(inventario.getStockMinimo());
        dto.setStockMaximo(inventario.getStockMaximo());

        // Mapeo de objetos relacionados a sus DTOs de salida
        dto.setAlmacen(toAlmacenSalida(inventario.getAlmacen()));
        dto.setMedicamento(toMedicamentoSalida(inventario.getMedicamento()));

        return dto;
    }

    // --- Métodos de Interfaz (CRUD) ---

    @Override
    @Transactional(readOnly = true)
    public List<Inventario_Salida> obtenerTodos() {
        return inventarioRepository.findAll().stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Inventario_Salida> obtenerTodosPaginados(Pageable pageable) {
        Page<Inventario> page = inventarioRepository.findAll(pageable);
        List<Inventario_Salida> inventariosDto = page.getContent().stream()
                .map(this::toDto)
                .collect(Collectors.toList());
        return new PageImpl<>(inventariosDto, page.getPageable(), page.getTotalElements());
    }

    @Override
    @Transactional(readOnly = true)
    public Inventario_Salida obtenerPorId(Integer id) {
        return inventarioRepository.findById(id)
                .map(this::toDto)
                .orElse(null);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Inventario_Salida> obtenerPorAlmacenId(Integer id) {
        return inventarioRepository.findByAlmacenId(id).stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<Inventario_Salida> obtenerPorMedicamentoId(Integer id) {
        return inventarioRepository.findByMedicamentoId(id).stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public Inventario_Salida crear(Inventario_Guardar inventarioGuardar) {
        Inventario nuevoInventario = new Inventario();
        nuevoInventario.setStockActual(inventarioGuardar.getStockActual());
        nuevoInventario.setStockMinimo(inventarioGuardar.getStockMinimo());
        nuevoInventario.setStockMaximo(inventarioGuardar.getStockMaximo());

        // --- LÓGICA CONSOLIDADA (Crear) ---
        // 1. Buscar y asignar Almacén
        Almacen almacen = almacenRepository.findById(inventarioGuardar.getAlmacenId())
                .orElseThrow(() -> new RuntimeException("Almacen con ID " + inventarioGuardar.getAlmacenId() + " no encontrado"));
        nuevoInventario.setAlmacen(almacen);

        // 2. Buscar y asignar Medicamento
        Medicamento medicamento = medicamentoRepository.findById(inventarioGuardar.getMedicamentoId())
                .orElseThrow(() -> new RuntimeException("Medicamento con ID " + inventarioGuardar.getMedicamentoId() + " no encontrado"));
        nuevoInventario.setMedicamento(medicamento);
        // ------------------------------------

        Inventario guardado = inventarioRepository.save(nuevoInventario);
        return toDto(guardado);
    }

    @Override
    public Inventario_Salida editar(Inventario_Modificar inventarioModificar) {
        Inventario inventarioExistente = inventarioRepository.findById(inventarioModificar.getId())
                .orElseThrow(() -> new RuntimeException("Inventario ID " + inventarioModificar.getId() + " no encontrado para modificar"));

        inventarioExistente.setStockActual(inventarioModificar.getStockActual());
        inventarioExistente.setStockMinimo(inventarioModificar.getStockMinimo());
        inventarioExistente.setStockMaximo(inventarioModificar.getStockMaximo());

        // --- LÓGICA CONSOLIDADA (Editar) ---
        // 1. Buscar y asignar Almacén
        Almacen almacen = almacenRepository.findById(inventarioModificar.getAlmacenId())
                .orElseThrow(() -> new RuntimeException("Almacen con ID " + inventarioModificar.getAlmacenId() + " no encontrado"));
        inventarioExistente.setAlmacen(almacen);

        // 2. Buscar y asignar Medicamento
        Medicamento medicamento = medicamentoRepository.findById(inventarioModificar.getMedicamentoId())
                .orElseThrow(() -> new RuntimeException("Medicamento con ID " + inventarioModificar.getMedicamentoId() + " no encontrado"));
        inventarioExistente.setMedicamento(medicamento);
        // ------------------------------------

        Inventario actualizado = inventarioRepository.save(inventarioExistente);
        return toDto(actualizado);
    }

    @Override
    public void eliminarPorId(Integer id) {
        inventarioRepository.deleteById(id);
    }
}