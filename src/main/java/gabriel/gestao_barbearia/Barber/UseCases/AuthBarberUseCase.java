package gabriel.gestao_barbearia.Barber.UseCases;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import gabriel.gestao_barbearia.Barber.Repositories.BarberRepository;
import gabriel.gestao_barbearia.Barber.dto.AuthBarberDTO;
import gabriel.gestao_barbearia.Barber.dto.AuthBarberResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;

@Service
public class AuthBarberUseCase {

    @Value("${security.token.secret}")
    private String secretKey;

    @Autowired
    private BarberRepository barberRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public AuthBarberResponseDTO execute (AuthBarberDTO authBarberDTO) {
        var Barber = this.barberRepository.findByUsername(authBarberDTO.getUsername()).orElseThrow(
                () -> {
                    throw new UsernameNotFoundException("Barber not found.");
                }
        );
        //verificar a senha
        var passwordMatcher = this.passwordEncoder.matches(authBarberDTO.getPassword(), Barber.getPassword());

        //se não forem iguais → erro
        if (!passwordMatcher){
            throw new BadCredentialsException("Username/Password incorrect.");
        }

        //se forem iguais → gerar token
        Algorithm algorithm = Algorithm.HMAC256(secretKey);

        var expiration = Instant.now().plus(Duration.ofHours(2));

        var token = JWT.create()
                .withIssuer("java-barber")
                .withSubject(Barber.getId().toString())
                .withExpiresAt(Instant.now().plus(Duration.ofHours(2)))
                .withClaim("roles", Arrays.asList("BARBER"))
                .sign(algorithm);

        var authBarberResponse = AuthBarberResponseDTO.builder()
                .access_token(token)
                .expires_in(expiration.toEpochMilli())
                .build();

        return authBarberResponse;
    }
}
