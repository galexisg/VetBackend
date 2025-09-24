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
import com.Vet.VetBackend.proveedores.domain.Proveedor;
import com.Vet.VetBackend.usuarios.domain.Usuario;
import com.Vet.VetBackend.usuarios.repo.UsuarioRepository;
//import com.Vet.VetBackend.usuarios.web.dto.Usuario_Salida;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.List;
import java.util.Optional;
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
        dto.setId(entidad.getId());
        dto.setCantidad(entidad.getCantidad());
        dto.setCostoUnitario(entidad.getCostoUnitario());
        dto.setFecha(entidad.getFecha());

        if (entidad.getMedicamento() != null) {
            MedicamentoSalida medDto = new MedicamentoSalida();
            medDto.setId(entidad.getMedicamento().getId());
            medDto.setNombre(entidad.getMedicamento().getNombre());
            dto.setMedicamento(medDto);
        }

        if (entidad.getAlmacen() != null) {
            Almacen_Salida almDto = new Almacen_Salida();
            almDto.setId(entidad.getAlmacen().getId());
            almDto.setNombre(entidad.getAlmacen().getNombre());
            dto.setAlmacen(almDto);
        }

        if (entidad.getLoteMedicamento() != null) {
            Lotes_medicamentos lote = entidad.getLoteMedicamento();
            LoteMedicamento_Salida loteDto = new LoteMedicamento_Salida();
            loteDto.setId(lote.getId());
            loteDto.setCodigoLote(lote.getCodigoLote());
            if (lote.getFechaVencimiento() != null)
                loteDto.setFechaVencimiento(new Date(lote.getFechaVencimiento().getTime()).toLocalDate());
            loteDto.setObservaciones(lote.getObservaciones());
            loteDto.setMedicamentoId(lote.getMedicamento().getId());
            loteDto.setMedicamentoNombre(lote.getMedicamento().getNombre());
            if (lote.getProveedor() != null) {
                loteDto.setProveedorId(lote.getProveedor().getId());
                loteDto.setProveedorNombre(lote.getProveedor().getNombre());
            }
            dto.setLoteMedicamento(loteDto);
        }

        if (entidad.getMovimientoInventario() != null) {
            MovimientoInventario_Salida movDto = new MovimientoInventario_Salida();
            movDto.setId(entidad.getMovimientoInventario().getId());
            dto.setMovimientoInventario(movDto);
        }

        if (entidad.getUsuario() != null) {
            Usuario_Salida userDto = new Usuario_Salida();
            userDto.setId(entidad.getUsuario().getId());
            userDto.setNombreCompleto(entidad.getUsuario().getNombreCompleto());
            userDto.setCorreo(entidad.getUsuario().getCorreo());
            userDto.setNickName(entidad.getUsuario().getNickName());
            dto.setUsuario(userDto);
        }

        return dto;
    }

    @Override
    public List<MovimientoDetalle_Salida> obtenerTodos() {
        return movimientoDetalleRepository.findAll().stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public Page<MovimientoDetalle_Salida> obtenerTodosPaginados(Pageable pageable) {
        Page<MovimientoDetalle> page = movimientoDetalleRepository.findAll(pageable);
        List<MovimientoDetalle_Salida> dtos = page.getContent().stream()
                .map(this::toDto)
                .collect(Collectors.toList());
        return new PageImpl<>(dtos, pageable, page.getTotalElements());
    }

    @Override
    public MovimientoDetalle_Salida obtenerPorId(Integer id) {
        return movimientoDetalleRepository.findById(id)
                .map(this::toDto)
                .orElse(null);
    }

    @Override
    public List<MovimientoDetalle_Salida> obtenerPorMovimientoInventarioId(Integer id) {
        return movimientoDetalleRepository.findByMovimientoInventario_Id(id).stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<MovimientoDetalle_Salida> obtenerPorMedicamentoId(Integer id) {
        return movimientoDetalleRepository.findByMedicamento_Id(id).stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<MovimientoDetalle_Salida> obtenerPorLoteMedicamento(Integer id) {
        return movimientoDetalleRepository.findByLoteMedicamento_Id(id).stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<MovimientoDetalle_Salida> obtenerPorUsuarioId(Integer id) {
        return movimientoDetalleRepository.findByUsuario_Id(id).stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Override
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
    public void eliminarPorId(Integer id) {
        movimientoDetalleRepository.deleteById(id);
    }
}
