package co.com.project.infraestructure.entrypoints.controllers.giftcards;

import co.com.project.domain.exception.InvalidDataException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class HandlerRestController {

    @ExceptionHandler({InvalidDataException.class})
    public ResponseEntity<Object> invalidDataException(Exception ex, WebRequest request) {
        return new ResponseEntity<>(ex.getMessage(), new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({AuthorizationDeniedException.class, AccessDeniedException.class})
    public ResponseEntity<Object> authorizationDeniedException(Exception ex, WebRequest request) {
        return new ResponseEntity<>(ex.getMessage(), new HttpHeaders(), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler({Throwable.class})
    public ResponseEntity<Object> handlerErrorInternal(Exception ex, WebRequest request) {
        return new ResponseEntity<>(ex.getMessage(), new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler({MethodArgumentNotValidException.class})
    public ResponseEntity<Object> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error ->
                errors.put(error.getField(), error.getDefaultMessage())
        );
        return ResponseEntity.badRequest().body(errors);
    }


}
