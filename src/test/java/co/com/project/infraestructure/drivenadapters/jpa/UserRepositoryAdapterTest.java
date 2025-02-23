package co.com.project.infraestructure.drivenadapters.jpa;

import co.com.project.domain.model.User;
import co.com.project.infraestructure.drivenadapters.jpa.user.RoleDataRepository;
import co.com.project.infraestructure.drivenadapters.jpa.user.UserDataRepository;
import co.com.project.infraestructure.drivenadapters.jpa.user.UserRepositoryAdapter;
import co.com.project.infraestructure.drivenadapters.jpa.user.entities.RoleData;
import co.com.project.infraestructure.drivenadapters.jpa.user.entities.UserData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserRepositoryAdapterTest {

    @Mock
    private UserDataRepository userDataRepository;

    @Mock
    private RoleDataRepository roleDataRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserRepositoryAdapter userRepositoryAdapter;

    UserData userData1;
    UserData userData2;
    User user;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        userData1 = new UserData(1L, "john", "password123", "john@example.com", true, List.of());
        userData2 = new UserData(2L, "jane", "password456", "jane@example.com",  false, List.of());
        user = new User();
        user.setId(1L);
        user.setPassword("password123");
    }

    @Test
    void testFindAllUsers() {

        when(userDataRepository.findAll()).thenReturn(List.of(userData1, userData2));

        List<User> result = userRepositoryAdapter.findAll();

        assertEquals(2, result.size());
        assertEquals("john", result.get(0).getUsername());
        assertEquals("jane", result.get(1).getUsername());
        verify(userDataRepository, times(1)).findAll();
    }

    @Test
    void testFindById() {
        when(userDataRepository.findById(anyLong())).thenReturn(Optional.of(userData1));

        User result = userRepositoryAdapter.findById(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("john", result.getUsername());
        verify(userDataRepository, times(1)).findById(1L);
    }

    @Test
    void testFindById_NotFound() {
        when(userDataRepository.findById(anyLong())).thenReturn(Optional.empty());

        User result = userRepositoryAdapter.findById(1L);

        assertNull(result);
        verify(userDataRepository, times(1)).findById(1L);
    }

    @Test
    void testSaveUser() {
        UserData userData = new UserData(1L, "pepe", "hashedPassword", "pepe@example.com", true, List.of());
        RoleData roleData = new RoleData(1L, "ROLE_USER");
        when(roleDataRepository.findByName(anyString())).thenReturn(Optional.of(roleData));
        when(passwordEncoder.encode(anyString())).thenReturn("hashedPassword");
        when(userDataRepository.save(any(UserData.class))).thenReturn(userData);

        User result = userRepositoryAdapter.save(user);

        assertNotNull(result);
        assertEquals("hashedPassword", result.getPassword());
        verify(roleDataRepository, times(1)).findByName("ROLE_USER");
        verify(passwordEncoder, times(1)).encode(anyString());
        verify(userDataRepository, times(1)).save(any(UserData.class));
    }
}
