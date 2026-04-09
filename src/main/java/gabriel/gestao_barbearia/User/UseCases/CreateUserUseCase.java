package gabriel.gestao_barbearia.User.UseCases;

import jakarta.validation.Valid;
import gabriel.gestao_barbearia.Exceptions.UserFoundException;
import gabriel.gestao_barbearia.User.Repositories.UserRepository;
import gabriel.gestao_barbearia.User.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

@Service
public class CreateUserUseCase {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    //o useCase herda o repository justamente para verificar através dele no db se existe o usuario correspondente
    public UserEntity execute(@Valid @RequestBody UserEntity userEntity){
        this.userRepository.findByUsernameOrEmail(userEntity.getUsername(), userEntity.getEmail()).ifPresent(user -> {
            throw new UserFoundException();
        });
        var password = passwordEncoder.encode(userEntity.getPassword());
        userEntity.setPassword(password);

        return this.userRepository.save(userEntity);
    }
}
