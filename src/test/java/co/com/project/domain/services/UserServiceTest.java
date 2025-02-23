package co.com.project.domain.services;

import co.com.project.domain.gateways.UserRepository;
import co.com.project.domain.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    User user;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setId(1L);
        user.setUsername("dodo");
        user.setEmail("example@example.com");
        user.setAdmin(true);

        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testListUsers() {
        List<User> users = List.of(user, user);

        when(userRepository.findAll()).thenReturn(users);

        List<User> result = userService.list();

        assertEquals(2, result.size());
        assertEquals("dodo", result.get(0).getUsername());
        verify(userRepository, times(1)).findAll();
    }


    @Test
    void testSaveUser() {
        when(userRepository.save(any(User.class))).thenReturn(user);

        User result = userService.save(user);

        assertNotNull(result);
        assertEquals("dodo", result.getUsername());
        verify(userRepository, times(1)).save(user);
    }

    @Test
    void testFindUserById() {
        when(userRepository.findById(anyLong())).thenReturn(user);

        User result = userService.findById(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("dodo", result.getUsername());
        verify(userRepository, times(1)).findById(1L);
    }
}