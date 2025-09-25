package com.Vet.VetBackend.movimientoDetalle.app.implementations;

import com.Vet.VetBackend.Medicamento.domain.Medicamento;
import com.Vet.VetBackend.Medicamento.repo.IMedicamentoRepository;
import com.Vet.VetBackend.Medicamento.web.dto.MedicamentoSalida;
import com.Vet.VetBackend.almacen.domain.Almacen;
import com.Vet.VetBackend.almacen.repo.IAlmacenRepository;
import com.Vet.VetBackend.almacen.web.dto.Almacen_Salida;
import com.Vet.VetBackend.lote_medicamentos.domain.Lotes_medicamentos;
import com.Vet.VetBackend.lote_medicamentos.repo.ILotesMedicamentosRepository;
import com.Vet.VetBackend.lote_medicamentos.web.dto.LoteMedicamento_Salida;
import com.Vet.VetBackend.movimientoDetalle.app.services.IMovimientoDetalleService;
import com.Vet.VetBackend.movimientoDetalle.domain.MovimientoDetalle;
import com.Vet.VetBackend.movimientoDetalle.repo.IMovimientoDetalleRepository;
import com.Vet.VetBackend.movimientoDetalle.web.dto.MovimientoDetalle_Guardar;
import com.Vet.VetBackend.movimientoDetalle.web.dto.MovimientoDetalle_Modificar;
import com.Vet.VetBackend.movimientoDetalle.web.dto.MovimientoDetalle_Salida;
import com.Vet.VetBackend.movimientoInventario.domain.MovimientoInventario;
import com.Vet.VetBackend.movimientoInventario.repo.IMovimientoInventarioRepository;
import com.Vet.VetBackend.movimientoInventario.web.dto.MovimientoInventario_Salida;
import com.Vet.VetBackend.usuarios.domain.Usuario;
import com.Vet.VetBackend.usuarios.repo.UsuarioRepository;
import com.Vet.VetBackend.usuarios.web.dto.UsuarioRes;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MovimientoDetalleService implements IMovimientoDetalleService {

    private final IMovimientoDetalleRepository movimientoDetalleRepository;
    private final IMedicamentoRepository medicamentoRepository;
    private final ILotesMedicamentosRepository loteMedicamentoRepository;
    private final IAlmacenRepository almacenRepository;
    private final IMovimientoInventarioRepository movimientoInventarioRepository;
    private final UsuarioRepository usuarioRepository;

    public MovimientoDetalleService(IMovimientoDetalleRepository movimientoDetalleRepository,
                                    IMedicamentoRepository medicamentoRepository,
                                    ILotesMedicamentosRepository loteMedicamentoRepository,
                                    IAlmacenRepository almacenRepository,
                                    IMovimientoInventarioRepository movimientoInventarioRepository,
                                    UsuarioRepository usuarioRepository) {
        this.movimientoDetalleRepository = movimientoDetalleRepository;
        this.medicamentoRepository = medicamentoRepository;
        this.loteMedicamentoRepository = loteMedicamentoRepository;
        this.almacenRepository = almacenRepository;
        this.movimientoInventarioRepository = movimientoInventarioRepository;
        this.usuarioRepository = usuarioRepository;
    }

    private MovimientoDetalle_Salida toDto(MovimientoDetalle entidad) {
        if (entidad == null) return null;

        MovimientoDetalle_Salida dto = new MovimientoDetalle_Salida();
        try {
            dto.setId(entidad.getId());
            dto.setCantidad(entidad.getCantidad());
            dto.setCostoUnitario(entidad.getCostoUnitario());
            dto.setFecha(entidad.getFecha());

            if (entidad.getMedicamento() != null) {
                Medicamento medicamento = entidad.getMedicamento();
                MedicamentoSalida medDto = new MedicamentoSalida();
                medDto.setId(medicamento.getId());
                medDto.setNombre(medicamento.getNombre());
                medDto.setFormafarmacautica(medicamento.getFormafarmacautica());
                medDto.setConcentracion(medicamento.getConcentracion());
                medDto.setUnidad(medicamento.getUnidad());
                medDto.setVia(medicamento.getVia());
                medDto.setRequiereReceta(medicamento.getRequiereReceta());
                medDto.setActivo(medicamento.getActivo());
                medDto.setTemperaturaalm(medicamento.getTemperaturaalm());
                medDto.setVidautilmeses(medicamento.getVidautilmeses());
                dto.setMedicamento(medDto);
            }

            if (entidad.getAlmacen() != null) {
                Almacen almacen = entidad.getAlmacen();
                Almacen_Salida almDto = new Almacen_Salida();
                almDto.setId(almacen.getId());
                almDto.setNombre(almacen.getNombre());
                almDto.setUbicacion(almacen.getUbicacion());
                almDto.setActivo(almacen.getActivo());
                dto.setAlmacen(almDto);
            }

            if (entidad.getLoteMedicamento() != null) {
                Lotes_medicamentos lote = entidad.getLoteMedicamento();
                LoteMedicamento_Salida loteDto = new LoteMedicamento_Salida();
                loteDto.setId(lote.getId());
                loteDto.setCodigoLote(lote.getCodigoLote());
                if (lote.getFechaVencimiento() != null) {
                    loteDto.setFechaVencimiento(Date.valueOf(new Date(lote.getFechaVencimiento().getTime()).toLocalDate()));
                }
                loteDto.setObservaciones(lote.getObservaciones());

                if (lote.getMedicamento() != null) {
                    loteDto.setMedicamentoId(lote.getMedicamento().getId());
                    loteDto.setMedicamentoNombre(lote.getMedicamento().getNombre());
                }

                if (lote.getProveedor() != null) {
                    loteDto.setProveedorId(lote.getProveedor().getId());
                    loteDto.setProveedorNombre(lote.getProveedor().getNombre());
                }

                dto.setLoteMedicamento(loteDto);
            }

            if (entidad.getMovimientoInventario() != null) {
                MovimientoInventario_Salida movDto = new MovimientoInventario_Salida();
                movDto.setId(entidad.getMovimientoInventario().getId());
                movDto.setFecha(entidad.getMovimientoInventario().getFecha());
                // ✅ CAMBIO: Convertimos el tipo (enum) a String para el DTO
                movDto.setTipo(String.valueOf(entidad.getMovimientoInventario().getTipo()));
                movDto.setObservacion(entidad.getMovimientoInventario().getObservacion());
                dto.setMovimientoInventario(movDto);
            }

            if (entidad.getUsuario() != null) {
                Usuario usuario = entidad.getUsuario();
                UsuarioRes userDto = new UsuarioRes();
                userDto.setId(usuario.getId());
                userDto.setNombreCompleto(usuario.getNombreCompleto());
                userDto.setCorreo(usuario.getCorreo());
                userDto.setNickName(usuario.getNickName());
                dto.setUsuario(userDto);
            }

        } catch (Exception e) {
            System.out.println("🔥 Error al convertir MovimientoDetalle ID: " + entidad.getId() + " a DTO");
            e.printStackTrace();
        }

        return dto;
    }

    @Override
    @Transactional(readOnly = true)
    public List<MovimientoDetalle_Salida> obtenerTodos() {
        return movimientoDetalleRepository.findAll().stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public Page<MovimientoDetalle_Salida> obtenerTodosPaginados(Pageable pageable) {
        Page<MovimientoDetalle> page = movimientoDetalleRepository.findAll(pageable);
        List<MovimientoDetalle_Salida> dtos = page.getContent().stream()
                .map(this::toDto)
                .collect(Collectors.toList());
        return new PageImpl<>(dtos, pageable, page.getTotalElements());
    }

    @Override
    @Transactional(readOnly = true)
    public MovimientoDetalle_Salida obtenerPorId(Integer id) {
        return movimientoDetalleRepository.findById(id)
                .map(this::toDto)
                .orElse(null);
    }

    @Override
    @Transactional(readOnly = true)
    public List<MovimientoDetalle_Salida> obtenerPorMovimientoInventarioId(Integer id) {
        return movimientoDetalleRepository.findByMovimientoInventario_Id(id).stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<MovimientoDetalle_Salida> obtenerPorMedicamentoId(Integer id) {
        return movimientoDetalleRepository.findByMedicamento_Id(id).stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<MovimientoDetalle_Salida> obtenerPorLoteMedicamento(Integer id) {
        return movimientoDetalleRepository.findByLoteMedicamento_Id(id).stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<MovimientoDetalle_Salida> obtenerPorUsuarioId(Integer id) {
        return movimientoDetalleRepository.findByUsuario_Id(id).stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public MovimientoDetalle_Salida crear(MovimientoDetalle_Guardar dto) {
        MovimientoDetalle entidad = new MovimientoDetalle();
        entidad.setCantidad(dto.getCantidad());
        entidad.setCostoUnitario(dto.getCostoUnitario());
        entidad.setFecha(dto.getFecha());

        Medicamento medicamento = medicamentoRepository.findById(dto.getMedicamentoId())
                .orElseThrow(() -> new RuntimeException("Medicamento no encontrado"));
        Lotes_medicamentos lote = loteMedicamentoRepository.findById(dto.getLoteMedicamentoId())
                .orElseThrow(() -> new RuntimeException("Lote de medicamento no encontrado"));
        Almacen almacen = almacenRepository.findById(dto.getAlmacenId())
                .orElseThrow(() -> new RuntimeException("Almacén no encontrado"));
        MovimientoInventario movimientoInventario = movimientoInventarioRepository.findById(dto.getMovimientoInventarioId())
                .orElseThrow(() -> new RuntimeException("Movimiento de inventario no encontrado"));
        Usuario usuario = usuarioRepository.findById(dto.getUsuarioId())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        entidad.setMedicamento(medicamento);
        entidad.setLoteMedicamento(lote);
        entidad.setAlmacen(almacen);
        entidad.setMovimientoInventario(movimientoInventario);
        entidad.setUsuario(usuario);

        // 4. Guardar la entidad en la base de datos
        MovimientoDetalle entidadGuardada = movimientoDetalleRepository.save(entidad);

        // 5. Mapear la entidad guardada de vuelta a un DTO de salida
        return toDto(entidadGuardada);
    }

    @Override
    @Transactional
    public MovimientoDetalle_Salida editar(MovimientoDetalle_Modificar dto) {
        // 1. Buscar la entidad existente por su ID
        MovimientoDetalle entidad = movimientoDetalleRepository.findById(dto.getId())
                .orElseThrow(() -> new RuntimeException("Detalle de movimiento no encontrado"));

        // 2. Actualizar las propiedades de la entidad con los valores del DTO
        entidad.setCantidad(dto.getCantidad());
        entidad.setCostoUnitario(dto.getCostoUnitario());
        entidad.setFecha(dto.getFecha());

        // 3. Si se proporcionan nuevos IDs, buscar y actualizar las relaciones
        // Si los IDs no son nulos en el DTO, significa que se quieren cambiar
        if (dto.getMedicamentoId() != null) {
            Medicamento medicamento = medicamentoRepository.findById(dto.getMedicamentoId())
                    .orElseThrow(() -> new RuntimeException("Medicamento no encontrado"));
            entidad.setMedicamento(medicamento);
        }

        if (dto.getLoteMedicamentoId() != null) {
            Lotes_medicamentos lote = loteMedicamentoRepository.findById(dto.getLoteMedicamentoId())
                    .orElseThrow(() -> new RuntimeException("Lote de medicamento no encontrado"));
            entidad.setLoteMedicamento(lote);
        }

        if (dto.getAlmacenId() != null) {
            Almacen almacen = almacenRepository.findById(dto.getAlmacenId())
                    .orElseThrow(() -> new RuntimeException("Almacén no encontrado"));
            entidad.setAlmacen(almacen);
        }

        if (dto.getMovimientoInventarioId() != null) {
            MovimientoInventario movimientoInventario = movimientoInventarioRepository.findById(dto.getMovimientoInventarioId())
                    .orElseThrow(() -> new RuntimeException("Movimiento de inventario no encontrado"));
            entidad.setMovimientoInventario(movimientoInventario);
        }

        if (dto.getUsuarioId() != null) {
            Usuario usuario = usuarioRepository.findById(dto.getUsuarioId())
                    .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
            entidad.setUsuario(usuario);
        }

        // 4. Guardar los cambios en la base de datos
        MovimientoDetalle entidadActualizada = movimientoDetalleRepository.save(entidad);

        // 5. Mapear la entidad actualizada de vuelta a un DTO de salida
        return toDto(entidadActualizada);
    }

    @Override
    @Transactional
    public void eliminarPorId(Integer id) {
        movimientoDetalleRepository.deleteById(id);
    }
}