package gabriel.gestao_barbearia.Barber.Repositories;

import gabriel.gestao_barbearia.Barber.BarberEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface BarberRepository extends JpaRepository<BarberEntity, UUID> {
    Optional<BarberEntity> findByUsernameOrEmail(String username, String email);
    Optional<BarberEntity> findByUsername(String username);
}
