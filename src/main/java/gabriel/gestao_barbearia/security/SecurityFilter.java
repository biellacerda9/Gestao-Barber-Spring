package gabriel.gestao_barbearia.security;

import gabriel.gestao_barbearia.providers.JWTProvider;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;

@Component //usado aqui pq não é diretamente uma regra de negócio, mas é essencial para o sistema funcionar
public class SecurityFilter extends OncePerRequestFilter {

    @Autowired
    private JWTProvider jwtProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        //SecurityContextHolder.getContext().setAuthentication(null);

        String header = request.getHeader("Authorization");

        if (request.getRequestURI().startsWith("/appointment")) {
            if (header != null) {
                //pego o ‘id’ de quem está acessando
                var token = this.jwtProvider.validateToken(header);

                if (token == null) {
                    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                    return;
                }

                //guardei o ‘id’ nessa variavel para poder usar em outras classes
                request.setAttribute("barber_id", token.getSubject());

                var roles = token.getClaim("roles").asList(Object.class);
                var grants = roles.stream()
                        .map(role -> new SimpleGrantedAuthority("ROLE_" + role.toString().toUpperCase())).toList();


                UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(token.getSubject(), null, grants);
                SecurityContextHolder.getContext().setAuthentication(auth);
            }
        }
        filterChain.doFilter(request, response);
    }
}

//o securityContextHolder funciona como uma pasta que armazena algumas informações: usuario, credenciais (senhas) e authorithies (permissões ou papeis que esse user tem, como ADMIN, CLIENTE)

//no codigo, uso isso quando crio o UsernamePasswordAuthenticationToken e coloco esse cracha dentro dessa pasta, o que
//'avisa' o spring que eu verifiquei o cara e passo o id dele

//o que facilita pq outras partes do codigo podem "perguntar" pro securityHolder quem está logado sem precisar ler o token de novo
