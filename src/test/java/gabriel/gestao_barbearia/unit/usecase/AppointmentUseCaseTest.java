package gabriel.gestao_barbearia.unit.usecase;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import gabriel.gestao_barbearia.appointment.AppointmentEntity;
import gabriel.gestao_barbearia.appointment.repositories.AppointmentRepository;
import gabriel.gestao_barbearia.appointment.useCase.AppointmentUseCase;

@ExtendWith(MockitoExtension.class) // liga o mockito ao JUnit
public class AppointmentUseCaseTest {

  @Mock
  private AppointmentRepository appointmentRepository; // dublê

  @InjectMocks
  private AppointmentUseCase appointmentUseCase; // injeta o mock na classe real

  // os dois cenarios que tenho que testar:
  // caso feliz: sem conflito, findByBarberIdAndDate retorna vazio (empty), e save
  // retorna o proprio objeto
  // caso de erro: conflito, findByBarberIdAndDate retorna um objeto, e save nao é
  // chamado

  @Test
  public void saveWithoutConflict() {

    // dados
    UUID barberId = UUID.randomUUID();
    LocalDateTime date = LocalDateTime.now();

    // builda entidade
    AppointmentEntity appointmentEntity = AppointmentEntity.builder()
        .barberId(barberId)
        .date(date)
        .build();

    when(appointmentRepository.findByBarberIdAndDate(barberId, date)).thenReturn(Optional.empty());
    when(appointmentRepository.save(appointmentEntity)).thenReturn(appointmentEntity);

    // when
    AppointmentEntity result = appointmentUseCase.execute(appointmentEntity);

    // then
    assertEquals(appointmentEntity, result);
    verify(appointmentRepository).save(appointmentEntity);
  }

  @Test
  public void throwExceptionWhenAppointmentExists() {
    // given
    UUID barberId = UUID.randomUUID();
    LocalDateTime date = LocalDateTime.now();

    AppointmentEntity appointmentEntity = AppointmentEntity.builder()
        .barberId(barberId)
        .date(date)
        .build();

    AppointmentEntity existingAppointment = AppointmentEntity.builder()
        .barberId(barberId)
        .date(date)
        .build();

    when(appointmentRepository.findByBarberIdAndDate(barberId, date)).thenReturn(Optional.of(existingAppointment));

    // when / then
    assertThrows(RuntimeException.class, () -> appointmentUseCase.execute(appointmentEntity));
    verify(appointmentRepository, never()).save(any());
  }
}
