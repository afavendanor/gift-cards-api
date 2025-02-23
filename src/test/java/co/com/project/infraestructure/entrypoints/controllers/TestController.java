package co.com.project.infraestructure.entrypoints.controllers;

import co.com.project.domain.exception.InvalidDataException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/test")
public class TestController {

    @GetMapping("/invalid-data")
    public void throwInvalidDataException() {
        throw new InvalidDataException("Invalid data provided");
    }

    @GetMapping("/unauthorized")
    public void throwAuthorizationException() {
        throw new org.springframework.security.access.AccessDeniedException("Unauthorized access");
    }

    @GetMapping("/internal-error")
    public void throwInternalServerError() {
        throw new RuntimeException("Internal server error occurred");
    }

    @PostMapping("/validate")
    public void validateInput(@Valid @RequestBody TestRequest request) {
    }

    public static class TestRequest {
        @NotNull(message = "Field cannot be null")
        private String fieldName;
    }
}
