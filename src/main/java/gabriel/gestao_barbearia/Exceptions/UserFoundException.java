package gabriel.gestao_barbearia.Exceptions;

public class UserFoundException extends RuntimeException{
    public UserFoundException() {
        super ("Esse usuário já existe.");
    }
}
