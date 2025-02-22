package co.com.project.infraestructure.entrypoints.controllers.user;

import co.com.project.application.usecase.CreateUserUseCase;
import co.com.project.application.usecase.GettingListUserUseCase;
import co.com.project.domain.model.User;
import co.com.project.application.dtos.UserDTO;
import co.com.project.infraestructure.entrypoints.controllers.user.transformer.UserDTOTransformer;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@Tag(name = "User")
public class UserController {

    private final CreateUserUseCase createUserUseCase;
    private final GettingListUserUseCase gettingListUserUseCase;

    @GetMapping("/list")
    @Operation(summary = "Return list of users")
    public ResponseEntity<List<UserDTO>> list() {
        List<User> userList = gettingListUserUseCase.execute();
        return ResponseEntity.ok(UserDTOTransformer.INSTANCE.userListToUserDTOList(userList));
    }

    @PostMapping("/create")
    @Operation(summary = "Save information of a user admin")
    public ResponseEntity<?> create(@Valid @RequestBody UserDTO request) {
        User user = createUserUseCase.execute(UserDTOTransformer.INSTANCE.userDTOToUser(request), true);
        return ResponseEntity.ok(UserDTOTransformer.INSTANCE.userToUserDTO(user));
    }

    @PostMapping("/register")
    @Operation(summary = "Register information of a user")
    public ResponseEntity<?> register(@Valid @RequestBody UserDTO request) {
        User user = createUserUseCase.execute(UserDTOTransformer.INSTANCE.userDTOToUser(request), false);
        return ResponseEntity.ok(UserDTOTransformer.INSTANCE.userToUserDTO(user));
    }
}
