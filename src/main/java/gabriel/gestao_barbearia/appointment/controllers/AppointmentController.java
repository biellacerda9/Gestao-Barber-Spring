package gabriel.gestao_barbearia.appointment.controllers;

import gabriel.gestao_barbearia.appointment.AppointmentEntity;
import gabriel.gestao_barbearia.appointment.dto.AppointmentDTO;
import gabriel.gestao_barbearia.appointment.dto.DiaryResponseDTO;
import gabriel.gestao_barbearia.appointment.repositories.AppointmentRepository;
import gabriel.gestao_barbearia.appointment.useCase.AppointmentUseCase;
import gabriel.gestao_barbearia.appointment.useCase.DiaryResponseUseCase;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/appointment")
public class AppointmentController {
    @Autowired
    private AppointmentUseCase appointmentUseCase;

    @Autowired
    private DiaryResponseUseCase diaryResponseUseCase;

    @PostMapping("/")
    //@PreAuthorize("hasRole('USER')") //garante que o barbeiro não tente marcar horário como ele fosse cliente
    public ResponseEntity<Object> create (@Valid @RequestBody AppointmentDTO appointmentDTO, HttpServletRequest request) {
        var userId = request.getAttribute("user_id");

        var appointmentEntity = AppointmentEntity.builder()
                .userId(UUID.fromString(userId.toString()))
                .barberId(appointmentDTO.getBarberId())
                .date(appointmentDTO.getDate())
                .build();
        return ResponseEntity.ok(this.appointmentUseCase.execute(appointmentEntity));
    }

    @GetMapping("/")
    @PreAuthorize("hasRole('BARBER')")
    public ResponseEntity<Object> response (HttpServletRequest request) {
        var barberId = request.getAttribute("barber_id");

        try {
            var diaryResponse = this.diaryResponseUseCase.execute(UUID.fromString(barberId.toString()));
            return ResponseEntity.ok().body(diaryResponse);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
