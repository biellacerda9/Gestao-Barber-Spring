package gabriel.gestao_barbearia.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {

    @Autowired
    private SecurityFilter securityFilter;

    @Bean
    SecurityFilterChain securityFilterChain (HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable()) //desabilito a proteção de cookies para permitir que a API se comunique com a apliação através de um tokem
                .authorizeHttpRequests(auth -> { //autorizo os caminhos que eu quero permitir que o usuario consiga acessar e bloqueio os outros
                    auth.requestMatchers("/barber/**").permitAll();
                    auth.requestMatchers("/usuario/").permitAll();
                    auth.requestMatchers("/barber/auth").permitAll();
                    auth.requestMatchers("/usuario/auth").permitAll();
                    auth.requestMatchers("/appointment/").permitAll();
                    auth.anyRequest().authenticated();
                }).addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class); //pede ao spring para rodar o securityFilter ANTES de tentar fazer qualquer validação
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
