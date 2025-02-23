package co.com.project.infraestructure.entrypoints.controllers.health;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(name = "Health")
public class HealthController {

    @GetMapping(path = "/health")
    @Operation(summary = "Returns the application status")
    public ResponseEntity<?> getStatusAplication() {

        return new ResponseEntity("Up", HttpStatus.OK);
    }
}
