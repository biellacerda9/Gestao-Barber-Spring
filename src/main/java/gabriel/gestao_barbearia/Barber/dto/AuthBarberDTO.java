package gabriel.gestao_barbearia.Barber.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthBarberDTO {
    private String username;
    private String password;
}
