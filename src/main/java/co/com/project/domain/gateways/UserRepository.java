package co.com.project.domain.gateways;

import co.com.project.domain.model.User;
import java.util.List;

public interface UserRepository {

    List<User> findAll();

    User save(User user);

}
