package gabriel.gestao_barbearia.Barber.Controller;

import jakarta.validation.Valid;
import gabriel.gestao_barbearia.Barber.BarberEntity;
import gabriel.gestao_barbearia.Barber.UseCases.CreateBarberUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/barber")
public class BarberController {

    @Autowired
    private CreateBarberUseCase createBarberUseCase;

    @PostMapping("/")
    public ResponseEntity<Object> createBarber(@Valid @RequestBody BarberEntity barberEntity) {
        try {
            var result = this.createBarberUseCase.execute(barberEntity);
            return ResponseEntity.ok().body(result);
        }catch (Exception ex){
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }
}
