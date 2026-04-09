package gabriel.gestao_barbearia.User.UseCases;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import gabriel.gestao_barbearia.User.Repositories.UserRepository;
import gabriel.gestao_barbearia.User.dto.AuthUserRequestDTO;
import gabriel.gestao_barbearia.User.dto.AuthUserResponseDTO;
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
public class AuthUserUseCase {
    @Value("${security.token.secret}")
    private String tokenSecret;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public AuthUserResponseDTO execute (AuthUserRequestDTO authUserDTO){
        var user = this.userRepository.findByUsername(authUserDTO.username()).orElseThrow(
                () -> {
                    throw new UsernameNotFoundException("User not found");
                }
        );

        var passwordMatcher = passwordEncoder.matches(authUserDTO.password(), user.getPassword());

        if (!passwordMatcher) {
            throw new BadCredentialsException("Invalid username or password");
        }

        Algorithm algorithm = Algorithm.HMAC256(tokenSecret);

        var expiration = Instant.now().plus(Duration.ofMinutes(10));

        var token = JWT.create()
                .withIssuer("java-barber")
                .withSubject(user.getId().toString())
                .withExpiresAt(expiration)
                .withClaim("roles", Arrays.asList("USER"))
                .sign(algorithm);
        // o claim vai servir para usar o preAuth depois

        var authUserResponse = AuthUserResponseDTO.builder().access_token(token).expires_in(expiration.toEpochMilli()).build();

        return authUserResponse;
    }
}
