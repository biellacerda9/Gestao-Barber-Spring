package gabriel.gestao_barbearia.appointment.repositories;

import gabriel.gestao_barbearia.appointment.AppointmentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface AppointmentRepository extends JpaRepository<AppointmentEntity, UUID> {
    Optional<AppointmentEntity> findByBarberIdAndDate(UUID barberId, LocalDateTime date);
    List<AppointmentEntity> findByBarberId(UUID barberId);
}
