package gabriel.gestao_barbearia.appointment;

import gabriel.gestao_barbearia.Barber.BarberEntity;
import gabriel.gestao_barbearia.User.UserEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Entity(name = "appointments")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AppointmentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    //muitos agendamentos para um user
    @ManyToOne
    @JoinColumn(name = "user_id", insertable = false, updatable = false)
    private UserEntity userEntity;
    @Column(name = "user_id", nullable = false)
    private UUID userId;

    //muitos agendamentos para um barber
    @ManyToOne()
    @JoinColumn(name = "barber_id", insertable = false, updatable = false)
    private BarberEntity barberEntity;
    @Column(name = "barber_id")
    private UUID barberId;

    private LocalDateTime date;

    @CreationTimestamp
    private LocalDateTime createdAt;
}
