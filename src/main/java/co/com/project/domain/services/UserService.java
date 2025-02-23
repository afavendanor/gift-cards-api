package co.com.project.domain.services;

import co.com.project.domain.gateways.UserRepository;
import co.com.project.domain.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository repository;

    public List<User> list() {
        return repository.findAll();
    }

    public User save(User user) {
        return repository.save(user);
    }

    public User findById(Long id) {
        return repository.findById(id);
    }

}
