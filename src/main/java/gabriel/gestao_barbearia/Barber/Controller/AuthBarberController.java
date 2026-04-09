package gabriel.gestao_barbearia.Barber.Controller;

import gabriel.gestao_barbearia.Barber.UseCases.AuthBarberUseCase;
import gabriel.gestao_barbearia.Barber.dto.AuthBarberDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/barber")
public class AuthBarberController {

    @Autowired
    private AuthBarberUseCase  authBarberUseCase;

    @PostMapping("/auth")
    public ResponseEntity<Object> create (@RequestBody AuthBarberDTO authBarberDTO){
        try {
            var result = this.authBarberUseCase.execute(authBarberDTO);
            return ResponseEntity.ok().body(result);
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ex.getMessage());
        }
    }
}
