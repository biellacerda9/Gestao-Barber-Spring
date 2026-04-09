package gabriel.gestao_barbearia.appointment.useCase;

import gabriel.gestao_barbearia.User.Repositories.UserRepository;
import gabriel.gestao_barbearia.appointment.AppointmentEntity;
import gabriel.gestao_barbearia.appointment.dto.DiaryResponseDTO;
import gabriel.gestao_barbearia.appointment.repositories.AppointmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class DiaryResponseUseCase {
    @Autowired
    private AppointmentRepository appointmentRepository;
    @Autowired
    private UserRepository userRepository;

    public List<DiaryResponseDTO> execute (UUID barberID) {
        List<AppointmentEntity> appointments = this.appointmentRepository.findByBarberId(barberID);
        //pegar a lista
        var appointmentDTO = appointments.stream()
                .map(appointment -> {
                    //pegar o nome de cada um
                    var clientName = this.userRepository.findById(appointment.getUserId())
                            .map(user -> user.getName())
                            .orElseThrow(() -> new UsernameNotFoundException("Username not found"));
                    //buildar para criar o dto corretamente
                    return DiaryResponseDTO.builder().date(appointment.getDate()).name(clientName).build();
                }).collect(Collectors.toList());
        return appointmentDTO;
    }
}

//busca a lista de agendamentos no banco
//transforma a lista em dto e percorre ela
//faz mais uma busca dentro dela, agora pelo nome (tratar erro)
//retorna buildando o dto
//transforma em lista e retorna a lista
