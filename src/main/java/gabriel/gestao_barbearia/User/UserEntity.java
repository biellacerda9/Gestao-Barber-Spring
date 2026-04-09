package gabriel.gestao_barbearia.User;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Entity(name = "users")
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String name;
    @Email(message = "O campo [e-mail] deve conter um e-mail válido.")
    private String email;
    @Length(min = 10, max = 100, message = "A senha deve conter no mínimo [8] caracteres.")
    private String password;
    @Pattern(regexp = "\\S+", message = "O campo [username] não deve conter espaço.")
    private String username;
    private String phone;
    @CreationTimestamp
    private LocalDateTime createdAt;
}
