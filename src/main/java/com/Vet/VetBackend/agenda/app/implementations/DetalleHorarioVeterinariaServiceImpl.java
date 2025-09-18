//package com.Vet.VetBackend.agenda.app.implementations;
//
//import com.Vet.VetBackend.agenda.web.dto.DetalleHorarioVeterinarioGuardarReq;
//import com.Vet.VetBackend.agenda.web.dto.DetalleHorarioVeterinarioSalidaRes;
//import com.Vet.VetBackend.agenda.repo.DetalleHorarioVeterinarioRepository;
//import com.Vet.VetBackend.agenda.repo.VeterinarioRepository;
//import com.Vet.VetBackend.agenda.repo.DiaRepository;
//import com.Vet.VetBackend.agenda.repo.BloqueHorarioRepository;
//import com.Vet.VetBackend.agenda.app.implementations.DetalleHorarioVeterinarioServiceImpl;
//import com.Vet.VetBackend.agenda.domain.Veterinario;
//import com.Vet.VetBackend.agenda.domain.Dia;
//import com.Vet.VetBackend.agenda.domain.DetalleHorarioVeterinario;
//import com.Vet.VetBackend.agenda.domain.BloqueHorario;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//import org.springframework.web.server.ResponseStatusException;
//import org.springframework.http.HttpStatus;
//
//import java.util.List;
//import java.util.stream.Collectors;
//
//@Service
//public class DetalleHorarioVeterinarioServiceImpl implements DetalleHorarioVeterinarioServiceImpl {
//
//    @Autowired
//    private DetalleHorarioVeterinarioRepository detalleHorarioVeterinarioRepository;
//
//    @Autowired
//    private VeterinarioRepository veterinarioRepository;
//    @Autowired
//    private DiaRepository diaRepository;
//    @Autowired
//    private BloqueHorarioRepository bloqueHorarioRepository;
//
//    @Override
//    @Transactional(readOnly = true)
//    public List<DetalleHorarioVeterinarioSalidaRes> findAll() {
//        return detalleHorarioVeterinarioRepository.findAll().stream()
//                .map(this::convertirA_DtoSalida)
//                .collect(Collectors.toList());
//    }
//
//    @Override
//    @Transactional(readOnly = true)
//    public DetalleHorarioVeterinarioSalidaRes findById(Integer id) {
//        return detalleHorarioVeterinarioRepository.findById(id)
//                .map(this::convertirA_DtoSalida)
//                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "DetalleHorarioVeterinario no encontrado"));
//    }
//
//    @Override
//    @Transactional
//    public DetalleHorarioVeterinarioSalidaRes save(DetalleHorarioVeterinarioGuardarReq detalleDTO) {
//        // Se busca el veterinario usando el ID de tipo int del DTO
//        Veterinario veterinario = veterinarioRepository.findById(detalleDTO.getVeterinarioId())
//                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Veterinario no encontrado"));
//
//        Dia dia = diaRepository.findById(detalleDTO.getDiaId())
//                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Día no encontrado"));
//
//        // El ID de BloqueHorario es un Byte, se convierte a Integer para el repositorio
//        BloqueHorario bloqueHorario = bloqueHorarioRepository.findById(detalleDTO.getBloqueHorarioId().intValue())
//                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Bloque horario no encontrado"));
//
//        DetalleHorarioVeterinario detalle = new DetalleHorarioVeterinario();
//        detalle.setVeterinario(veterinario);
//        detalle.setDia(dia);
//        detalle.setBloqueHorario(bloqueHorario);
//
//        DetalleHorarioVeterinario guardado = detalleHorarioVeterinarioRepository.save(detalle);
//        return convertirA_DtoSalida(guardado);
//    }
//
//    @Override
//    @Transactional
//    public DetalleHorarioVeterinarioSalidaRes update(Integer id, DetalleHorarioVeterinarioGuardarReq detalleDTO) {
//        DetalleHorarioVeterinario detalle = detalleHorarioVeterinarioRepository.findById(id)
//                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "DetalleHorarioVeterinario a actualizar no encontrado"));
//
//        // Se busca el veterinario usando el ID de tipo int del DTO
//        Veterinario veterinario = veterinarioRepository.findById(detalleDTO.getVeterinarioId())
//                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Veterinario no encontrado"));
//
//        Dia dia = diaRepository.findById(detalleDTO.getDiaId())
//                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Día no encontrado"));
//
//        // El ID de BloqueHorario es un Byte, se convierte a Integer para el repositorio
//        BloqueHorario bloqueHorario = bloqueHorarioRepository.findById(detalleDTO.getBloqueHorarioId().intValue())
//                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Bloque horario no encontrado"));
//
//        detalle.setVeterinario(veterinario);
//        detalle.setDia(dia);
//        detalle.setBloqueHorario(bloqueHorario);
//
//        DetalleHorarioVeterinario actualizado = detalleHorarioVeterinarioRepository.save(detalle);
//        return convertirA_DtoSalida(actualizado);
//    }
//
//    @Override
//    @Transactional
//    public void deleteById(Integer id) {
//        detalleHorarioVeterinarioRepository.deleteById(id);
//    }
//
//    private DetalleHorarioVeterinarioSalidaRes convertirA_DtoSalida(DetalleHorarioVeterinario detalle) {
//        DetalleHorarioVeterinarioSalidaRes dto = new DetalleHorarioVeterinarioSalidaRes();
//
//        dto.setDetalleHorarioVeterinarioId(detalle.getDetalleHorarioVeterinarioId());
//
//        if (detalle.getVeterinario() != null) {
//            // Se asume que el ID del modelo Veterinario ya es Integer y el del DTO es int
//            dto.setVeterinarioId(detalle.getVeterinario().getId());
//            dto.setNombreVeterinario(detalle.getVeterinario().getNombreCompleto());
//        }
//        if (detalle.getDia() != null) {
//            dto.setDiaId(detalle.getDia().getDiaId());
//            dto.setNombre(detalle.getDia().getNombre());
//        }
//        if (detalle.getBloqueHorario() != null) {
//            // Se asume que el ID del modelo BloqueHorario es Integer y el del DTO es Byte
//            dto.setBloqueHorarioId(detalle.getBloqueHorario().getBloqueHorarioId().byteValue());
//            dto.setHoraInicio(detalle.getBloqueHorario().getInicio());
//            dto.setHoraFin(detalle.getBloqueHorario().getFin());
//        }
//
//        return dto;
//    }
//}
