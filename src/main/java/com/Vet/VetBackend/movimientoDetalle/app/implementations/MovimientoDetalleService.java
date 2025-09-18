/*package com.Vet.VetBackend.movimientoDetalle.app.implementations;

import com.Vet.VetBackend.movimientoDetalle.app.services.IMovimientoDetalleService;
import com.Vet.VetBackend.movimientoDetalle.domain.MovimientoDetalle;
import com.Vet.VetBackend.movimientoDetalle.repo.IMovimientoDetalleRepository;
import com.Vet.VetBackend.movimientoDetalle.web.dto.MovimientoDetalle_Modificar;
import com.Vet.VetBackend.movimientoDetalle.web.dto.MovimientoDetalle_Salida;
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
public class MovimientoDetalleService implements IMovimientoDetalleService {
    @Autowired
    private IMovimientoDetalleRepository movimientoDetalleRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private IMedicamentoRepository medicamentoRepository;

    @Autowired
    private ILotesMedicamentosRepository loteMedicamentoRepository;

    @Autowired
    private IAlmacenRepository almacenRepository;

    @Autowired
    private IMovimientoInventarioRepository movimientoInventarioRepository;


    @Override
    public List<MovimientoDetalle_Salida> obtenerTodos() {
        List<MovimientoDetalle> movimientoDetalles = movimientoDetalleRepository.findAll();
        return movimientoDetalles.stream()
                .map(movimientoDetalle -> modelMapper.map(movimientoDetalle, MovimientoDetalle_Salida.class))
                .collect(Collectors.toList());
    }

    @Override
    public Page<MovimientoDetalle_Salida> obtenerTodosPaginados(Pageable pageable) {
        Page<MovimientoDetalle> page = movimientoDetalleRepository.findAll(pageable);

        List<MovimientoDetalle_Salida> movimientoDetalleDtos = page.stream()
                .map(movimientoDetalle -> modelMapper.map(movimientoDetalle, MovimientoDetalle_Salida.class))
                .collect(Collectors.toList());
        return new PageImpl<>(movimientoDetalleDtos, page.getPageable(), page.getTotalElements());
    }

    @Override
    public MovimientoDetalle_Salida obtenerPorId(Integer id) {
        Optional<MovimientoDetalle> movimientoDetalle = movimientoDetalleRepository.findById(id);

        if(movimientoDetalle.isPresent()){
            return modelMapper.map(movimientoDetalle.get(), MovimientoDetalle_Salida.class);
        }
        return null;
    }

    @Override
    public List<MovimientoDetalle_Salida> obtenerPorMovimientoInventarioId(Integer id) {
        List<MovimientoDetalle> movimientoDetalles = movimientoDetalleRepository.findByMovimientoInventario_Id(id);
        return movimientoDetalles.stream()
                .map(movimientoDetalle -> modelMapper.map(movimientoDetalle, MovimientoDetalle_Salida.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<MovimientoDetalle_Salida> obtenerPorMedicamentoId(Integer id) {
        List<MovimientoDetalle> movimientoDetalles = movimientoDetalleRepository.findByMedicamento_Id(id);
        return movimientoDetalles.stream()
                .map(movimientoDetalle -> modelMapper.map(movimientoDetalle, MovimientoDetalle_Salida.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<MovimientoDetalle_Salida> obtenerPorLoteMedicamento(Integer id){
        List<MovimientoDetalle> movimientoDetalles = movimientoDetalleRepository.findByLotesMedicamentos_Id(id);
        return movimientoDetalles.stream()
                .map(movimientoDetalle -> modelMapper.map(movimientoDetalle, MovimientoDetalle_Salida.class))
                .collect(Collectors.toList());
    }

    @Override
    public MovimientoDetalle_Salida crear(MovimientoDetalle_Guardar dto) {
        // Buscar entidades relacionadas
        Medicamento medicamento = medicamentoRepository.findById(dto.getMedicamentoId())
                .orElseThrow(() -> new RuntimeException("Medicamento no encontrado"));

        Lotes_medicamentos lote = loteMedicamentoRepository.findById(dto.getLoteMedicamentoId())
                .orElseThrow(() -> new RuntimeException("Lote de Medicamento no encontrado"));

        Almacen almacen = almacenRepository.findById(dto.getAlmacenId())
                .orElseThrow(() -> new RuntimeException("Almacén no encontrado"));

        MovimientoInventario movimientoInventario = movimientoInventarioRepository.findById(dto.getMovimientoInventarioId())
                .orElseThrow(() -> new RuntimeException("Movimiento de inventario no encontrado"));

        // Crear entidad MovimientoDetalle
        MovimientoDetalle movimientoDetalle = new MovimientoDetalle();
        movimientoDetalle.setMedicamento(medicamento);
        movimientoDetalle.setLotesMedicamentos(lote);
        movimientoDetalle.setAlmacen(almacen);
        movimientoDetalle.setMovimientoInventario(movimientoInventario);
        movimientoDetalle.setCantidad(dto.getCantidad());
        movimientoDetalle.setFecha(dto.getFecha());
        movimientoDetalle.setUsuarioId(dto.getUsuarioId());

        // Guardar y devolver DTO de salida
        MovimientoDetalle guardado = movimientoDetalleRepository.save(movimientoDetalle);
        return modelMapper.map(guardado, MovimientoDetalle_Salida.class);
    }




    @Override
    public MovimientoDetalle_Salida editar(MovimientoDetalle_Modificar movimientoDetalleModificar) {
        MovimientoDetalle movimientoDetalle = movimientoDetalleRepository.save(modelMapper.map(movimientoDetalleModificar, MovimientoDetalle.class));
        return modelMapper.map(movimientoDetalle, MovimientoDetalle_Salida.class);
    }

    @Override
    public void eliminarPorId(Integer id) {
        movimientoDetalleRepository.deleteById(id);
    }
}*/