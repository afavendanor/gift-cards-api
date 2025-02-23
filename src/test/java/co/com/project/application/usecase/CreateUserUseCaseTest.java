package co.com.project.application.usecase;

import co.com.project.domain.model.User;
import co.com.project.domain.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class CreateUserUseCaseTest {

    private UserService userService;
    private CreateUserUseCase createUserUseCase;

    @BeforeEach
    void setUp() {
        userService = mock(UserService.class);
        createUserUseCase = new CreateUserUseCase(userService);
    }

    @Test
    void shouldCreateUserSuccessfully() {

        User user = new User();
        user.setEmail("test@example.com");
        User savedUser = new User();
        savedUser.setEmail("test@example.com");
        savedUser.setAdmin(true);
        savedUser.setEnable(true);
        when(userService.save(any(User.class))).thenReturn(savedUser);

        User result = createUserUseCase.execute(user, true);

        assertNotNull(result);
        assertEquals("test@example.com", result.getEmail());
        assertTrue(result.isAdmin());
        assertTrue(result.isEnable());

        verify(userService, times(1)).save(any(User.class));
    }

    @Test
    void shouldSetUserAsAdminWhenFlagIsTrue() {

        User user = new User();
        user.setEmail("admin@example.com");
        when(userService.save(any(User.class))).thenAnswer(invocation -> invocation.getArgument(0));

        User result = createUserUseCase.execute(user, true);

        assertTrue(result.isAdmin());
    }

    @Test
    void shouldNotSetUserAsAdminWhenFlagIsFalse() {
        User user = new User();
        user.setEmail("user@example.com");
        when(userService.save(any(User.class))).thenAnswer(invocation -> invocation.getArgument(0));

        User result = createUserUseCase.execute(user, false);

        assertFalse(result.isAdmin());
    }

    @Test
    void shouldEnableUserByDefault() {
        User user = new User();
        user.setEmail("enabled@example.com");
        ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);
        when(userService.save(userCaptor.capture())).thenAnswer(invocation -> invocation.getArgument(0));

        createUserUseCase.execute(user, false);

        assertTrue(userCaptor.getValue().isEnable());
    }
}
