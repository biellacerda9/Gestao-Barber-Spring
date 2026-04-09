package gabriel.gestao_barbearia.User.Controller;

import gabriel.gestao_barbearia.User.UseCases.AuthUserUseCase;
import gabriel.gestao_barbearia.User.dto.AuthUserRequestDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/usuario")
public class AuthUserController {
    @Autowired
    private AuthUserUseCase authUserUseCase;

    @PostMapping("/auth")
    public ResponseEntity<Object> create (@RequestBody AuthUserRequestDTO authUserDTO){
        try {
            var result = this.authUserUseCase.execute(authUserDTO);
            return ResponseEntity.ok().body(result);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
    }
}
