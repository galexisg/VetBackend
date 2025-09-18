//package com.Vet.VetBackend.lote_medicamentos.app.implementations;
//
//import com.Vet.VetBackend.lote_medicamentos.domain.Lotes_medicamentos;
//import com.Vet.VetBackend.lote_medicamentos.repo.ILotesMedicamentosRepository;
//import com.Vet.VetBackend.lote_medicamentos.app.services.ILotesMedicamentoService;
//import com.Vet.VetBackend.lote_medicamentos.web.dto.LoteMedicamento_Salida;
//import com.Vet.VetBackend.lote_medicamentos.web.dto.LoteMedicamentos_Actualizar;
//import com.Vet.VetBackend.lote_medicamentos.web.dto.LoteMedicamentos_Guardar;
//import org.modelmapper.ModelMapper;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.PageImpl;
//import org.springframework.data.domain.Pageable;
//import org.springframework.stereotype.Service;
//
//import java.time.LocalDate;
//import java.util.List;
//import java.util.stream.Collectors;
//
//@Service
//public class LotesMedicamentoService implements ILotesMedicamentoService {
//
//    @Autowired
//    private ILotesMedicamentosRepository lotesMedicamentosRepository;
//
//    @Autowired
//    private ModelMapper modelMapper;
//
//    @Override
//    public List<LoteMedicamento_Salida> obtenerTodos() {
//        List<Lotes_medicamentos> loteMedicamentoSalidas = lotesMedicamentosRepository.findAll();
//
//        return loteMedicamentoSalidas.stream()
//                .map(lote -> modelMapper.map(lote, LoteMedicamento_Salida.class)) // ✅ corregido
//                .collect(Collectors.toList());
//    }
//
//    @Override
//    public Page<LoteMedicamento_Salida> obtenerTodosPaginados(Pageable pageable) {
//        Page<Lotes_medicamentos> page = lotesMedicamentosRepository.findAll(pageable);
//
//        List<LoteMedicamento_Salida> LoteMedicamentoDto = page.stream()
//                .map(lote -> modelMapper.map(lote, LoteMedicamento_Salida.class)) // ✅ corregido
//                .collect(Collectors.toList());
//
//        return new PageImpl<>(LoteMedicamentoDto, page.getPageable(), page.getTotalElements());
//    }
//
//    @Override
//    public LoteMedicamento_Salida obtenerPorId(Integer id) {
//        Lotes_medicamentos lote = lotesMedicamentosRepository.findById(id)
//                .orElseThrow(() -> new RuntimeException("Lote no encontrado")); // ✅ mejor manejo
//        return modelMapper.map(lote, LoteMedicamento_Salida.class);
//    }
//
//    @Override
//    public LoteMedicamento_Salida crear(LoteMedicamentos_Guardar loteMedicamentosGuardar) {
//        Lotes_medicamentos lotesMedicamentos = modelMapper.map(loteMedicamentosGuardar, Lotes_medicamentos.class);
//        lotesMedicamentos = lotesMedicamentosRepository.save(lotesMedicamentos);
//        return modelMapper.map(lotesMedicamentos, LoteMedicamento_Salida.class);
//    }
//
//    @Override
//    public LoteMedicamento_Salida editar(LoteMedicamentos_Actualizar loteMedicamentosActualizar) {
//        Lotes_medicamentos existente = lotesMedicamentosRepository.findById(loteMedicamentosActualizar.getId())
//                .orElseThrow(() -> new RuntimeException("Lote no encontrado")); // ✅ validación previa
//
//        modelMapper.map(loteMedicamentosActualizar, existente); // ✅ actualiza campos
//        Lotes_medicamentos actualizado = lotesMedicamentosRepository.save(existente);
//        return modelMapper.map(actualizado, LoteMedicamento_Salida.class);
//    }
//
//    @Override
//    public void eliminarPorId(Integer id) {
//        lotesMedicamentosRepository.deleteById(id);
//    }
//
//    @Override
//    public List<LoteMedicamento_Salida> obtenerPorMedicamentoId(Integer medicamentoId) {
//        return lotesMedicamentosRepository.findByMedicamentoId(medicamentoId).stream() // ✅ implementado
//                .map(lote -> modelMapper.map(lote, LoteMedicamento_Salida.class))
//                .collect(Collectors.toList());
//    }
//
//    @Override
//    public List<LoteMedicamento_Salida> obtenerPorProveedorId(Integer proveedorId) {
//        return lotesMedicamentosRepository.findByProveedorId(proveedorId).stream() // ✅ implementado
//                .map(lote -> modelMapper.map(lote, LoteMedicamento_Salida.class))
//                .collect(Collectors.toList());
//    }
//
//    @Override
//    public List<LoteMedicamento_Salida> obtenerLotesProximosAVencer() {
//        LocalDate hoy = LocalDate.now();
//        LocalDate limite = hoy.plusDays(30); // ✅ rango de vencimiento
//        return lotesMedicamentosRepository.findByFechaVencimientoBetween(hoy, limite).stream()
//                .map(lote -> modelMapper.map(lote, LoteMedicamento_Salida.class))
//                .collect(Collectors.toList());
//    }
//}
//
//
////package Servicios.implementaciones;
////
////import INVENTARIO.Modelos.Lotes_medicamentos;
////import INVENTARIO.Repositorios.ILotesMedicamentosRepository;
////import INVENTARIO.Servicios.interfaces.ILotesMedicamentoService;
////import INVENTARIO.dtos.LotesMedicamentos.LoteMedicamento_Salida;
////import INVENTARIO.dtos.LotesMedicamentos.LoteMedicamentos_Actualizar;
////import INVENTARIO.dtos.LotesMedicamentos.LoteMedicamentos_Guardar;
////import org.modelmapper.ModelMapper;
////import org.springframework.beans.factory.annotation.Autowired;
////import org.springframework.data.domain.Page;
////import org.springframework.data.domain.PageImpl;
////import org.springframework.data.domain.Pageable;
////import org.springframework.stereotype.Service;
////
////import java.util.List;
////import java.util.stream.Collectors;
////
////@Service
////public class LotesMedicamentoService implements ILotesMedicamentoService {
////     @Autowired
////     private ILotesMedicamentosRepository lotesMedicamentosRepository;
////
////     @Autowired
////     private ModelMapper modelMapper;
////
////    @Override
////    public List<LoteMedicamento_Salida> obtenerTodos() {
////        List<Lotes_medicamentos> loteMedicamentoSalidas = lotesMedicamentosRepository.findAll();
////
////        return loteMedicamentoSalidas.stream()
////                .map(loteMedicamentoSalida -> modelMapper.map(loteMedicamentoSalidas, LoteMedicamento_Salida.class))
////                .collect(Collectors.toList());
////    }
////
////    @Override
////    public Page<LoteMedicamento_Salida> obtenerTodosPaginados(Pageable pageable) {
////        Page<Lotes_medicamentos> page = lotesMedicamentosRepository.findAll(pageable);
////
////        //LoteMedicamentoDto puede ser otro nombre tambien no afecta
////        List<LoteMedicamento_Salida> LoteMedicamentoDto = page.stream()
////                .map(lotesMedicamentos -> modelMapper.map(lotesMedicamentos, LoteMedicamento_Salida.class))
////                .collect(Collectors.toList());
////
////        return new PageImpl<>(LoteMedicamentoDto, page.getPageable(), page.getTotalElements());
////    }
////
////    @Override
////    public LoteMedicamento_Salida obtenerPorId(Integer id) {
////        return modelMapper.map(lotesMedicamentosRepository.findById(id).get(), LoteMedicamento_Salida.class);
////    }
////
////    @Override
////    public LoteMedicamento_Salida crear(LoteMedicamentos_Guardar loteMedicamentosGuardar) {
////        Lotes_medicamentos lotesMedicamentos = lotesMedicamentosRepository.save(modelMapper.map(loteMedicamentosGuardar, Lotes_medicamentos.class));
////        return modelMapper.map(lotesMedicamentos, LoteMedicamento_Salida.class);
////    }
////
////    @Override
////    public LoteMedicamento_Salida editar(LoteMedicamentos_Actualizar loteMedicamentosActualizar) {
////        Lotes_medicamentos lotesMedicamentos = lotesMedicamentosRepository.save(modelMapper.map(loteMedicamentosActualizar, Lotes_medicamentos.class));
////        return modelMapper.map(lotesMedicamentos, LoteMedicamento_Salida.class);
////    }
////
////    @Override
////    public void eliminarPorId(Integer id) {
////        lotesMedicamentosRepository.deleteById(id);
////    }
////
////    @Override
////    public List<LoteMedicamento_Salida> obtenerPorMedicamentoId(Integer medicamentoId) {
////        return List.of();
////    }
////
////    @Override
////    public List<LoteMedicamento_Salida> obtenerPorProveedorId(Integer proveedorId) {
////        return List.of();
////    }
////
////    @Override
////    public List<LoteMedicamento_Salida> obtenerLotesProximosAVencer() {
////        return List.of();
////    }
////}
