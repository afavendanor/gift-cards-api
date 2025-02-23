package co.com.project.application.usecase;

import co.com.project.domain.model.User;
import co.com.project.domain.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class GettingListUserUseCaseTest {

    private UserService userService;
    private GettingListUserUseCase gettingListUserUseCase;

    @BeforeEach
    void setUp() {
        userService = mock(UserService.class);
        gettingListUserUseCase = new GettingListUserUseCase(userService);
    }

    @Test
    void shouldReturnListOfUsersWhenExists() {
        User user1 = new User();
        user1.setId(1L);
        user1.setUsername("Alice");
        User user2 = new User();
        user2.setId(2L);
        user2.setUsername("Bob");
        List<User> mockUsers = Arrays.asList(user1, user2);
        when(userService.list()).thenReturn(mockUsers);

        List<User> result = gettingListUserUseCase.execute();

        assertNotNull(result);
        assertEquals(2, result.size());
        verify(userService, times(1)).list();
    }

    @Test
    void shouldReturnEmptyListWhenNoUsersExist() {
        when(userService.list()).thenReturn(Collections.emptyList());

        List<User> result = gettingListUserUseCase.execute();

        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(userService, times(1)).list();
    }
}
