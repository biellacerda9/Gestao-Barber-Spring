package gabriel.gestao_barbearia.integration;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.test.context.ActiveProfiles;
import org.testcontainers.containers.PostgreSQLContainer;

@SpringBootTest // -> sobe o contexto do springboot, injetando as dependencias, e permitindo que
                // eu use o repositorio real
@ActiveProfiles("test") // -> chama o application-X.properties correto, nesse caso o
                        // application-test.properties --- o X no caso é o nome que vem depois do hifen,
                        // no caso "test"
public abstract class AbstractIntegrationTest {

  // container compartilhado por TODAS as classes que estendem essa base --
  // início manual (sem @Container/@Testcontainers) porque a extensão do JUnit
  // gerencia o ciclo de vida do container POR CLASSE de teste, o que faz com
  // que classes com configuração de contexto diferente (@AutoConfigureMockMvc,
  // webEnvironment=RANDOM_PORT) disparem reinício do container -- a porta muda,
  // e o pool de conexão de um contexto Spring já cacheado (reaproveitado por
  // outra classe com a MESMA config) fica apontando pra uma porta antiga que
  // não existe mais. Início manual + sem stop() explícito (a JVM encerra o
  // container sozinha via shutdown hook do Testcontainers/Ryuk) evita isso.
  @ServiceConnection // -> spring detecta que é um container de banco e configura a conexão
                     // automaticamente
  static final PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:16-alpine");

  static {
    postgres.start();
  }
}
