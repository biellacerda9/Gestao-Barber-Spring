package gabriel.gestao_barbearia.e2e;

import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import gabriel.gestao_barbearia.integration.AbstractIntegrationTest;
import io.restassured.RestAssured;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT) // escolhe uma porta aleatoria pra subir e
                                                                            // evitar conflitos

public class AppointmentFlowTest extends AbstractIntegrationTest {

  // pega a porta aleatoria que o springboot escolheu pra subir o servidor pra
  // poder configurar o RestAssured com a porta correta
  @LocalServerPort
  private int port;

  @BeforeEach
  void setup() {
    RestAssured.port = port; // configura o RestAssured com a porta correta

    // rest assured é o que vai permitir que eu faça requisições http pro meu
    // servidor e consiga testar o fluxo completo de forma simulada
  }

  @Test
  void shouldCreateAppointment() {
    // aqui eu vou escrever o teste de ponta a ponta do fluxo de criar um
    // agendamento

    // 1 . criar barbeiro
    UUID barberId = createBarber();

    // 2 . criar usuario
    createUser("cliente_teste", "senha-segura-123");

    // 3 . login - pegar token
    String token = given()
        .contentType("application/json")
        .body("""
            {
              "username": "cliente_teste",
              "password": "senha-segura-123"
            }
            """)
        .when()
        .post("/usuario/auth")
        .then()
        .statusCode(200)
        .extract()
        .jsonPath()
        .getString("access_token");
    // 4 . criar agendamento
    given()
        .contentType("application/json")
        .header("Authorization", "Bearer " + token)
        .body("""
            {
              "barberId": "%s",
              "date": "2024-06-01T10:00:00"
            }
            """.formatted(barberId))
        .when()
        .post("/appointment/")
        .then()
        .statusCode(200)
        .body("barberId", equalTo(barberId.toString()));
  }

  // faz uma req real ao endpoint e retona o id
  private UUID createBarber() {
    String requestBody = """
        {
          "name": "Barbeiro Teste",
          "email": "barbeiro@teste.com",
          "password": "senha-segura-123",
          "username": "barbeiro_teste",
          "description": "Especialista em corte"
        }
        """;

    return given()
        .contentType("application/json")
        .body(requestBody)
        .when()
        .post("/barber/")
        .then()
        .statusCode(200)
        .extract()
        .jsonPath()
        .getUUID("id");
  }

  private void createUser(String username, String password) {
    String requestBody = """
        {
          "name": "Cliente Teste",
          "email": "cliente@teste.com",
          "password": "%s",
          "username": "%s"
        }
        """.formatted(password, username);

    given()
        .contentType("application/json")
        .body(requestBody)
        .when()
        .post("/usuario/")
        .then()
        .statusCode(200);
  }
}
