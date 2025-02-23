package co.com.project.application.usecase;

import co.com.project.domain.exception.InvalidDataException;
import co.com.project.domain.model.GiftCard;
import co.com.project.domain.model.User;
import co.com.project.domain.model.enums.GiftCardStatus;
import co.com.project.domain.services.GiftCardService;
import co.com.project.domain.services.NotificationService;
import co.com.project.domain.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class CreateGiftCardUseCaseTest {

    private GiftCardService giftCardService;
    private NotificationService notificationService;
    private UserService userService;
    private CreateGiftCardUseCase createGiftCardUseCase;

    private User user;
    private GiftCard giftCard;

    @BeforeEach
    void setUp() {
        giftCardService = mock(GiftCardService.class);
        notificationService = mock(NotificationService.class);
        userService = mock(UserService.class);
        createGiftCardUseCase = new CreateGiftCardUseCase(giftCardService, notificationService, userService);
        user = new User();
        user.setId(1L);
        user.setEmail("test@example.com");
        user.setAdmin(false);
        user.setEnable(false);

        giftCard = new GiftCard();
        giftCard.setId(1L);
        giftCard.setAmount(100D);
        giftCard.setUserId(1L);
        giftCard.setExpirationDate(LocalDateTime.now().plusDays(30));
        giftCard.setStatus(GiftCardStatus.ACTIVE);
    }

    @Test
    void shouldCreateGiftCardSuccessfully() {

        when(userService.findById(anyLong())).thenReturn(user);
        when(giftCardService.saveGiftCard(any(GiftCard.class))).thenReturn(giftCard);

        GiftCard result = createGiftCardUseCase.execute(giftCard);

        assertNotNull(result);
        assertEquals(100.0, result.getAmount());
        assertNotNull(result.getCreationDate());
        verify(giftCardService, times(1)).saveGiftCard(any(GiftCard.class));
        verify(notificationService, times(1)).sendNotification(eq(user.getEmail()), anyString(), anyString());
    }

    @Test
    void shouldAssignCurrentDateIfCreationDateIsNull() {

        when(userService.findById(anyLong())).thenReturn(user);
        when(giftCardService.saveGiftCard(any(GiftCard.class))).thenReturn(giftCard);

        GiftCard result = createGiftCardUseCase.execute(giftCard);

        assertNotNull(result.getCreationDate());
    }

    @Test
    void shouldThrowExceptionWhenUserNotFound() {

        giftCard.setUserId(99L);
        when(userService.findById(anyLong())).thenReturn(null);

        InvalidDataException exception = assertThrows(InvalidDataException.class, () -> createGiftCardUseCase.execute(giftCard));

        assertEquals("The user id 99 is not registered in the system", exception.getMessage());
        verify(giftCardService, never()).saveGiftCard(any());
        verify(notificationService, never()).sendNotification(anyString(), anyString(), anyString());
    }

    @Test
    void shouldSendNotificationAfterSavingGiftCard() {

        when(userService.findById(anyLong())).thenReturn(user);
        when(giftCardService.saveGiftCard(any(GiftCard.class))).thenReturn(giftCard);

        ArgumentCaptor<String> emailCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<String> subjectCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<String> bodyCaptor = ArgumentCaptor.forClass(String.class);

        createGiftCardUseCase.execute(giftCard);

        verify(notificationService).sendNotification(emailCaptor.capture(), subjectCaptor.capture(), bodyCaptor.capture());

        assertEquals("test@example.com", emailCaptor.getValue());
        assertTrue(subjectCaptor.getValue().contains("¡Tu Gift Card está lista!"));
        System.out.println(bodyCaptor.getValue().contains("Monto: $ 100,00"));
    }
}
