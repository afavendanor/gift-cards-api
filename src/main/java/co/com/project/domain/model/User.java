package co.com.project.domain.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import java.util.List;

@RequiredArgsConstructor
@Getter
@Setter
public class User {
    private Long id;
    private String username;
    private String password;
    private String email;
    private boolean enable;
    private boolean admin;
    private List<Role> roles;
}
