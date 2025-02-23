package co.com.project.infraestructure.drivenadapters.jpa;

import co.com.project.infraestructure.drivenadapters.jpa.user.UserDataRepository;
import co.com.project.infraestructure.drivenadapters.jpa.user.UserDetailRepositoryAdapter;
import co.com.project.infraestructure.drivenadapters.jpa.user.entities.RoleData;
import co.com.project.infraestructure.drivenadapters.jpa.user.entities.UserData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserDetailRepositoryAdapterTest {

    @Mock
    private UserDataRepository userDataRepository;

    @InjectMocks
    private UserDetailRepositoryAdapter userDetailRepositoryAdapter;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testLoadUserByUsername_Success() {
        UserData userData = new UserData(1L, "john_doe", "password123", "john_doe@example.com", true,
                List.of(new RoleData(1L, "ROLE_USER")));
        when(userDataRepository.findByUsername(anyString())).thenReturn(Optional.of(userData));

        UserDetails userDetails = userDetailRepositoryAdapter.loadUserByUsername("john_doe");

        assertNotNull(userDetails);
        assertEquals("john_doe", userDetails.getUsername());
        assertEquals("password123", userDetails.getPassword());
        assertTrue(userDetails.isEnabled());
        assertEquals(1, userDetails.getAuthorities().size());
        assertTrue(userDetails.getAuthorities().stream().anyMatch(auth -> auth.getAuthority().equals("ROLE_USER")));
        verify(userDataRepository, times(1)).findByUsername("john_doe");
    }

    @Test
    void testLoadUserByUsername_UserNotFound() {
        when(userDataRepository.findByUsername("unknown_user")).thenReturn(Optional.empty());

        Exception exception = assertThrows(UsernameNotFoundException.class,
                () -> userDetailRepositoryAdapter.loadUserByUsername("unknown_user"));

        assertEquals("Username unknown_user does not exist in the system", exception.getMessage());
        verify(userDataRepository, times(1)).findByUsername("unknown_user");
    }
}
