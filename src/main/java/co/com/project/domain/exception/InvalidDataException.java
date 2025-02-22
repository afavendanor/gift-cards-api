package co.com.project.domain.exception;

public class InvalidDataException extends RuntimeException{

    private static final long serialVersionUID = 1L;

    public InvalidDataException(String mensaje) {
        super(mensaje);
    }

}
