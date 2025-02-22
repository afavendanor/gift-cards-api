package co.com.project.application.usecase;

import co.com.project.domain.model.User;
import co.com.project.domain.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class GettingListUserUseCase {

    private final UserService service;

    public List<User> execute() {
        return service.list();
    }

}
