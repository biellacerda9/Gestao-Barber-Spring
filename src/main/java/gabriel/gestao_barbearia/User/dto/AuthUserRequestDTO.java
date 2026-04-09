package gabriel.gestao_barbearia.User.dto;

import lombok.AllArgsConstructor;
import lombok.Data;


public record AuthUserRequestDTO (String username, String password) {}
