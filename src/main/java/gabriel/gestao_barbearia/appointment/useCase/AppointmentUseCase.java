package gabriel.gestao_barbearia.appointment.useCase;

import gabriel.gestao_barbearia.appointment.AppointmentEntity;
import gabriel.gestao_barbearia.appointment.repositories.AppointmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AppointmentUseCase {

    @Autowired
    private AppointmentRepository appointmentRepository;

    public AppointmentEntity execute (AppointmentEntity appointmentEntity) {
        this.appointmentRepository.findByBarberIdAndDate(appointmentEntity.getBarberId(), appointmentEntity.getDate()).ifPresent(appointment -> {
            throw new RuntimeException("Appointment already exists");
        });
        return this.appointmentRepository.save(appointmentEntity);
    }
}
