package co.com.project.infraestructure.drivenadapters.jpa.user;

import co.com.project.infraestructure.drivenadapters.jpa.user.entities.UserData;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Component
public class UserDetailRepositoryAdapter implements UserDetailsService {

    private final UserDataRepository userDataRepository;

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UserData> userDataOptional = userDataRepository.findByUsername(username);
        if (userDataOptional.isEmpty()) {
            throw new UsernameNotFoundException(String.format("Username %s does not exist in the system", username));
        }
        UserData user = userDataOptional.orElseThrow();

        List<GrantedAuthority> authorities = user.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority(role.getName()))
                .collect(Collectors.toList());

        return new User(user.getUsername(), user.getPassword(), user.isEnable(),
                true, true, true, authorities);
    }
}
