package gabriel.gestao_barbearia.integration;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.test.context.ActiveProfiles;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@SpringBootTest // -> sobe o contexto do springboot, injetando as dependencias, e permitindo que
                // eu use o repositorio real
@ActiveProfiles("test") // -> chama o application-X.properties correto, nesse caso o
                        // application-test.properties --- o X no caso é o nome que vem depois do hifen,
                        // no caso "test"
@Testcontainers
public abstract class AbstractIntegrationTest {

  // classe pra subir o container do postgres uma vez e ser reutilizado em todos
  // os testes -> evita duplicar a configuração do Testcontainers em cada teste
  @Container // -> pra ser visto pelo testcontainer
  @ServiceConnection // -> spring detecta que é um container de banco e configura a conexão
                     // automaticamente
  static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:16-alpine");
}
