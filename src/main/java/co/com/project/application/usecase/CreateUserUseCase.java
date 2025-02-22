package co.com.project.application.usecase;

import co.com.project.domain.model.User;
import co.com.project.domain.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CreateUserUseCase {

    private final UserService service;

    public User execute(User user, boolean isAdmin) {
        user.setAdmin(isAdmin);
        user.setEnable(true);
        return service.save(user);
    }
}
