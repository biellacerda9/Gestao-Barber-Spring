package gabriel.gestao_barbearia.User.Controller;

import gabriel.gestao_barbearia.User.UseCases.ProfileUserUseCase;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import gabriel.gestao_barbearia.User.UseCases.CreateUserUseCase;
import gabriel.gestao_barbearia.User.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/usuario")
public class UserController {

    @Autowired
    private CreateUserUseCase createUserUseCase;
    @Autowired
    private ProfileUserUseCase profileUserUseCase;

    @PostMapping("/")
    public ResponseEntity<Object> createUser(@Valid @RequestBody UserEntity userEntity){
        try {
            var result = this.createUserUseCase.execute(userEntity);
            return ResponseEntity.ok().body(result);
        } catch (Exception ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @GetMapping("/")
    @PreAuthorize("hasRole('USER')")// so candidato pode acessar essa rota
    public ResponseEntity<Object> get (HttpServletRequest request){
        var idUser = request.getAttribute("user_id");
        try {
            var profile = this.profileUserUseCase.execute(UUID.fromString(idUser.toString()));
            return ResponseEntity.ok().body(profile);
        }catch (Exception ex){
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }
}
