package gabriel.gestao_barbearia.Barber.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AuthBarberResponseDTO {
    private String access_token;
    private Long expires_in;
}
