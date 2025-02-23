package co.com.project.infraestructure.drivenadapters.jpa.user;

import co.com.project.domain.gateways.UserRepository;
import co.com.project.domain.model.Role;
import co.com.project.domain.model.User;
import co.com.project.infraestructure.drivenadapters.jpa.user.entities.RoleData;
import co.com.project.infraestructure.drivenadapters.jpa.user.transformer.RoleDataTransformer;
import co.com.project.infraestructure.drivenadapters.jpa.user.entities.UserData;
import co.com.project.infraestructure.drivenadapters.jpa.user.transformer.UserDataTransformer;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Repository
public class UserRepositoryAdapter implements UserRepository {

    private final UserDataRepository userDataRepository;
    private final RoleDataRepository roleDataRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional(readOnly = true)
    public List<User> findAll() {
        return UserDataTransformer.INSTANCE.userDataListToUserList((List<UserData>) userDataRepository.findAll());
    }

    @Override
    @Transactional
    public User save(User user) {
        Optional<RoleData> optionalRoleUser = roleDataRepository.findByName("ROLE_USER");
        List<Role> roles = new ArrayList<>();
        optionalRoleUser.ifPresent(role -> {
            roles.add(RoleDataTransformer.INSTANCE.roleDataToRole(role));
        });
        if (user.isAdmin()) {
            Optional<RoleData> optionalRolAdmin = roleDataRepository.findByName("ROLE_ADMIN");
            optionalRolAdmin.ifPresent(role -> {
                roles.add(RoleDataTransformer.INSTANCE.roleDataToRole(role));
            });
        }
        user.setRoles(roles);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return UserDataTransformer.INSTANCE.userDataToUser(
                userDataRepository.save(UserDataTransformer.INSTANCE.userToUserData(user))
        );
    }

    @Override
    public User findById(Long id) {
        return UserDataTransformer.INSTANCE.userDataToUser(
                userDataRepository.findById(id).orElse(null)
        );
    }
}
