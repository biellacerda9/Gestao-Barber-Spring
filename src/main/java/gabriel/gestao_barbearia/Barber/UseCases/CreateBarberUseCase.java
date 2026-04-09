package gabriel.gestao_barbearia.Barber.UseCases;

import jakarta.validation.Valid;
import gabriel.gestao_barbearia.Barber.BarberEntity;
import gabriel.gestao_barbearia.Exceptions.UserFoundException;
import gabriel.gestao_barbearia.Barber.Repositories.BarberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

@Service
public class CreateBarberUseCase {
    @Autowired
    private BarberRepository barberRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public BarberEntity execute(@Valid @RequestBody BarberEntity barberEntity) {
        this.barberRepository.findByUsernameOrEmail(barberEntity.getUsername(), barberEntity.getEmail()).ifPresent(user -> {
            throw new UserFoundException();
        });

        var password = passwordEncoder.encode(barberEntity.getPassword());
        barberEntity.setPassword(password);

        return this.barberRepository.save(barberEntity);
    }
}
