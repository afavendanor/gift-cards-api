package co.com.project.infraestructure.drivenadapters.jpa.user;

import co.com.project.infraestructure.drivenadapters.jpa.user.entities.UserData;
import org.springframework.data.repository.CrudRepository;
import java.util.Optional;

public interface UserDataRepository extends CrudRepository<UserData, Long> {

    Optional<UserData> findByUsername(String username);

}
