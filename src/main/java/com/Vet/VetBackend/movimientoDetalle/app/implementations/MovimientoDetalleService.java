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
import com.Vet.VetBackend.movimientoDetalle.web.dto.Usuario_Salida;
import com.Vet.VetBackend.movimientoInventario.domain.MovimientoInventario;
import com.Vet.VetBackend.movimientoInventario.repo.IMovimientoInventarioRepository;
import com.Vet.VetBackend.movimientoInventario.web.dto.MovimientoInventario_Salida;
import com.Vet.VetBackend.usuarios.domain.Usuario;
import com.Vet.VetBackend.usuarios.repo.UsuarioRepository;
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
            } else {
                System.out.println("⚠️ Medicamento nulo en MovimientoDetalle ID: " + entidad.getId());
            }

            if (entidad.getAlmacen() != null) {
                Almacen almacen = entidad.getAlmacen();
                Almacen_Salida almDto = new Almacen_Salida();
                almDto.setId(almacen.getId());
                almDto.setNombre(almacen.getNombre());
                almDto.setUbicacion(almacen.getUbicacion());
                almDto.setActivo(almacen.getActivo());
                dto.setAlmacen(almDto);
            } else {
                System.out.println("⚠️ Almacén nulo en MovimientoDetalle ID: " + entidad.getId());
            }

            if (entidad.getLoteMedicamento() != null) {
                Lotes_medicamentos lote = entidad.getLoteMedicamento();
                LoteMedicamento_Salida loteDto = new LoteMedicamento_Salida();
                loteDto.setId(lote.getId());
                loteDto.setCodigoLote(lote.getCodigoLote());
                if (lote.getFechaVencimiento() != null) {
                    loteDto.setFechaVencimiento(Date.valueOf(new Date(lote.getFechaVencimiento().getTime()).toLocalDate()));
                } else {
                    System.out.println("⚠️ Fecha de vencimiento nula en Lote ID: " + lote.getId());
                }
                loteDto.setObservaciones(lote.getObservaciones());

                if (lote.getMedicamento() != null) {
                    loteDto.setMedicamentoId(lote.getMedicamento().getId());
                    loteDto.setMedicamentoNombre(lote.getMedicamento().getNombre());
                } else {
                    System.out.println("⚠️ Medicamento nulo en Lote ID: " + lote.getId());
                }

                if (lote.getProveedor() != null) {
                    loteDto.setProveedorId(lote.getProveedor().getId());
                    loteDto.setProveedorNombre(lote.getProveedor().getNombre());
                } else {
                    System.out.println("⚠️ Proveedor nulo en Lote ID: " + lote.getId());
                }

                dto.setLoteMedicamento(loteDto);
            } else {
                System.out.println("⚠️ Lote de medicamento nulo en MovimientoDetalle ID: " + entidad.getId());
            }

            if (entidad.getMovimientoInventario() != null) {
                MovimientoInventario_Salida movDto = new MovimientoInventario_Salida();
                movDto.setId(entidad.getMovimientoInventario().getId());
                movDto.setFecha(entidad.getMovimientoInventario().getFecha());
                // ✅ CAMBIO: Convertimos el tipo (enum) a String para el DTO
                movDto.setTipo(String.valueOf(entidad.getMovimientoInventario().getTipo()));
                movDto.setObservacion(entidad.getMovimientoInventario().getObservacion());
                dto.setMovimientoInventario(movDto);
            } else {
                System.out.println("⚠️ MovimientoInventario nulo en MovimientoDetalle ID: " + entidad.getId());
            }

            if (entidad.getUsuario() != null) {
                Usuario usuario = entidad.getUsuario();
                Usuario_Salida userDto = new Usuario_Salida();
                userDto.setId(usuario.getId());
                userDto.setNombreCompleto(usuario.getNombreCompleto());
                userDto.setCorreo(usuario.getCorreo());
                userDto.setNickName(usuario.getNickName());
                dto.setUsuario(userDto);
            } else {
                System.out.println("⚠️ Usuario nulo en MovimientoDetalle ID: " + entidad.getId());
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

        return toDto(movimientoDetalleRepository.save(entidad));
    }

    @Override
    @Transactional
    public MovimientoDetalle_Salida editar(MovimientoDetalle_Modificar dto) {
        MovimientoDetalle entidad = movimientoDetalleRepository.findById(dto.getId())
                .orElseThrow(() -> new RuntimeException("Detalle de movimiento no encontrado"));

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

        return toDto(movimientoDetalleRepository.save(entidad));
    }

    @Override
    @Transactional
    public void eliminarPorId(Integer id) {
        movimientoDetalleRepository.deleteById(id);
    }
}