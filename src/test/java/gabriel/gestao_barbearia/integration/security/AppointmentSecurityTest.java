package gabriel.gestao_barbearia.integration.security;

import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import gabriel.gestao_barbearia.integration.AbstractIntegrationTest;

@AutoConfigureMockMvc
public class AppointmentSecurityTest extends AbstractIntegrationTest {

  @Autowired
  private MockMvc mockMvc; // mockMvc simula a requisicao http sem subir servidor real

  // primeiro cenario -> sem token
  @Test
  void shouldDenyAcessWithoutToken() throws Exception {
    mockMvc.perform(get("/appointment/"))
        .andExpect(status().isForbidden());
  }

  // segundo cenario -> com token invalido
  @Test
  void shouldDenyAcessWithInvalidToken() throws Exception {
    mockMvc.perform(get("/appointment/")
        .header("Authorization", "Bearer invalid_token"))
        .andExpect(status().isUnauthorized());
  }

  // terceiro cenario -> com token valido

  private String generateValidToken(String subject, String role) {
    Algorithm algorithm = Algorithm.HMAC256("test-secret-key-not-used-in-production");
    return JWT.create()
        .withSubject(subject)
        .withClaim("roles", List.of(role))
        .sign(algorithm);
  }

  @Test
  void shouldAllowAcessWithValidTokenAndBarberRole() throws Exception {
    String token = generateValidToken(UUID.randomUUID().toString(), "BARBER");

    mockMvc.perform(get("/appointment/")
        .header("Authorization", "Bearer " + token))
        .andExpect(status().isOk());
  }

  // quarto cenario -> com token valido mas sem role
  @Test
  void shouldDenyAcessWithValidTokenButWithoutRole() throws Exception {
    String token = generateValidToken(UUID.randomUUID().toString(), ""); // sem role
    mockMvc.perform(get("/appointment/")
        .header("Authorization", "Bearer " + token))
        .andExpect(status().isForbidden());
  }
}
