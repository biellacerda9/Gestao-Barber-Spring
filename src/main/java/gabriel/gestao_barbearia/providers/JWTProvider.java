package gabriel.gestao_barbearia.providers;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;


@Service
public class JWTProvider {

    @Value("${security.token.secret}")
    private String secretKey; //chave que tranca e destranca o JWT (JWT verifica o token)

    public DecodedJWT validateToken(String token) {
        token = token.replace("Bearer ", "");
        Algorithm algorithm = Algorithm.HMAC256(secretKey); //recalcula a criptografia do token atraves da minha senha
        try {
            var tokenDecoded = JWT.require(algorithm).build().verify(token); //prepara o jwt com minha senha, builda, verifica o token e pega o sujeito (ID)
            return tokenDecoded;

        } catch (JWTVerificationException exception) {
            exception.printStackTrace();
            return null;
        }

    }
}
