package gabriel.gestao_barbearia.User.UseCases;

import gabriel.gestao_barbearia.User.Repositories.UserRepository;
import gabriel.gestao_barbearia.User.dto.ProfileUserResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ProfileUserUseCase {

    @Autowired
    private UserRepository userRepository;

    public ProfileUserResponseDTO execute (UUID idUser) {
        var user = this.userRepository.findById(idUser).orElseThrow(
                () -> {
                    throw new UsernameNotFoundException("User not found.");
                }
        );
        var userDto = ProfileUserResponseDTO.builder()
                .name(user.getName())
                .username(user.getUsername())
                .email(user.getEmail())
                .id(user.getId())
                .build();
        return userDto;
    }
}
