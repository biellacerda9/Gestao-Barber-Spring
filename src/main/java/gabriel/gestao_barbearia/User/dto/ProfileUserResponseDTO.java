package gabriel.gestao_barbearia.User.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProfileUserResponseDTO {
    private String name;
    private String email;
    private String username;
    private UUID id;
}
