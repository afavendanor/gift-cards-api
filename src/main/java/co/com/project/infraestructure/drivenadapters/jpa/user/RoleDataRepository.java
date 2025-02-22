package co.com.project.infraestructure.drivenadapters.jpa.user;

import co.com.project.infraestructure.drivenadapters.jpa.user.entities.RoleData;
import org.springframework.data.repository.CrudRepository;
import java.util.Optional;

public interface RoleDataRepository extends CrudRepository<RoleData, Long> {

    Optional<RoleData> findByName(String name);

}
