package gabriel.gestao_barbearia.integration.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import gabriel.gestao_barbearia.Barber.BarberEntity;
import gabriel.gestao_barbearia.Barber.Repositories.BarberRepository;
import gabriel.gestao_barbearia.User.UserEntity;
import gabriel.gestao_barbearia.User.Repositories.UserRepository;
import gabriel.gestao_barbearia.appointment.AppointmentEntity;
import gabriel.gestao_barbearia.appointment.repositories.AppointmentRepository;
import gabriel.gestao_barbearia.integration.AbstractIntegrationTest;

//extende AbstractIntegrationTest pra herdar a configuração do Testcontainers e subir o container do postgres
class AppointmentRepositoryTest extends AbstractIntegrationTest {

  // agora nao uso mais mockado, uso o repositorio real, que vai se conectar ao
  // banco de dados do container do postgres

  @Autowired
  private AppointmentRepository appointmentRepository;

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private BarberRepository barberRepository;

  @Test
  void shouldFindAppointmentByBarberAndDate() {
    // given
    UserEntity user = userRepository.save(criarUser());
    BarberEntity barber = barberRepository.save(criarBarber());
    LocalDateTime date = LocalDateTime.now();

    AppointmentEntity appointment = AppointmentEntity.builder()
        .userId(user.getId())
        .barberId(barber.getId())
        .date(date)
        .build();
    appointmentRepository.save(appointment);

    // when
    Optional<AppointmentEntity> result = appointmentRepository.findByBarberIdAndDate(barber.getId(), date);

    // then
    // assertThat é como fosse um "espero que"
    assertThat(result).isPresent(); // espero que o resultado esteja presente
    assertThat(result.get().getBarberId()).isEqualTo(barber.getId()); // espero que o id do barbeiro do resultado seja
                                                                      // igual ao id do barbeiro que eu salvei
  }

  @Test
  void shouldntFindAppointmentByBarberAndDate() {
    // given
    BarberEntity barber = barberRepository.save(criarBarber());

    // when
    Optional<AppointmentEntity> result = appointmentRepository.findByBarberIdAndDate(barber.getId(),
        LocalDateTime.now());

    // then
    assertThat(result).isEmpty();
  }

  @Test
  void shouldListAllAppointmentsForABarber() {
    // given
    UserEntity user = userRepository.save(criarUser());
    BarberEntity barber = barberRepository.save(criarBarber());

    // criar dois agendamentos para o mesmo barbeiro
    appointmentRepository.save(AppointmentEntity.builder()
        .userId(user.getId())
        .barberId(barber.getId())
        .date(LocalDateTime.now())
        .build());
    appointmentRepository.save(AppointmentEntity.builder()
        .userId(user.getId())
        .barberId(barber.getId())
        .date(LocalDateTime.now().plusDays(1))
        .build());

    // when
    List<AppointmentEntity> result = appointmentRepository.findByBarberId(barber.getId());

    // then
    assertThat(result).hasSize(2);
  }

  private UserEntity criarUser() {
    UserEntity user = new UserEntity();
    user.setName("Cliente Teste");
    user.setEmail("cliente@teste.com");
    user.setPassword("senha-segura-123");
    user.setUsername("cliente_teste");
    user.setPhone("11999999999");
    return user;
  }

  private BarberEntity criarBarber() {
    BarberEntity barber = new BarberEntity();
    barber.setName("Barbeiro Teste");
    barber.setEmail("barbeiro@teste.com");
    barber.setPassword("senha-segura-123");
    barber.setUsername("barbeiro_teste");
    barber.setDescription("Especialista em corte");
    return barber;
  }
}
