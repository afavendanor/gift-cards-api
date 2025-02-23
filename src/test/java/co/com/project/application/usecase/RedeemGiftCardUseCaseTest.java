package co.com.project.application.usecase;

import co.com.project.domain.exception.InvalidDataException;
import co.com.project.domain.model.GiftCard;
import co.com.project.domain.model.User;
import co.com.project.domain.model.enums.GiftCardStatus;
import co.com.project.domain.services.GiftCardService;
import co.com.project.domain.services.NotificationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class RedeemGiftCardUseCaseTest {

    private GiftCardService giftCardService;
    private NotificationService notificationService;
    private RedeemGiftCardUseCase redeemGiftCardUseCase;

    @BeforeEach
    void setUp() {
        giftCardService = mock(GiftCardService.class);
        notificationService = mock(NotificationService.class);
        redeemGiftCardUseCase = new RedeemGiftCardUseCase(giftCardService, notificationService);
    }

    @Test
    void shouldRedeemGiftCardWithSufficientBalance() {
        GiftCard giftCard = new GiftCard();
        giftCard.setId(1L);
        giftCard.setAmount(100.0);
        giftCard.setStatus(GiftCardStatus.ACTIVE);
        giftCard.setExpirationDate(LocalDateTime.now().plusDays(30));
        User user = new User();
        user.setEmail("user@example.com");
        giftCard.setUser(user);
        when(giftCardService.getGiftCard(anyLong())).thenReturn(giftCard);

        GiftCard result = redeemGiftCardUseCase.execute(1L, 50.0);

        assertNotNull(result);
        assertEquals(50.0, result.getAmount());
        assertEquals(GiftCardStatus.ACTIVE, result.getStatus());
        verify(giftCardService, times(1)).saveGiftCard(any(GiftCard.class));
        verify(notificationService, times(1)).sendNotification(eq("user@example.com"), anyString(), anyString());
    }

    @Test
    void shouldRedeemGiftCardWithExactBalance() {
        GiftCard giftCard = new GiftCard();
        giftCard.setId(1L);
        giftCard.setAmount(50.0);
        giftCard.setStatus(GiftCardStatus.ACTIVE);
        giftCard.setExpirationDate(LocalDateTime.now().plusDays(30));
        User user = new User();
        user.setEmail("user@example.com");
        giftCard.setUser(user);
        when(giftCardService.getGiftCard(anyLong())).thenReturn(giftCard);

        GiftCard result = redeemGiftCardUseCase.execute(1L, 50.0);

        assertNotNull(result);
        assertEquals(0.0, result.getAmount());
        assertEquals(GiftCardStatus.REDEEMED, result.getStatus());
        verify(giftCardService, times(1)).saveGiftCard(any(GiftCard.class));
        verify(notificationService, times(1)).sendNotification(eq("user@example.com"), anyString(), anyString());
    }

    @Test
    void shouldThrowExceptionWhenRedeemAmountIsGreaterThanBalance() {

        GiftCard giftCard = new GiftCard();
        giftCard.setId(1L);
        giftCard.setAmount(30.0);
        giftCard.setStatus(GiftCardStatus.ACTIVE);
        when(giftCardService.getGiftCard(anyLong())).thenReturn(giftCard);

        InvalidDataException exception = assertThrows(InvalidDataException.class, () ->
                redeemGiftCardUseCase.execute(1L, 50.0));

        assertEquals("The redeemable value is greater than the balance", exception.getMessage());
        verify(giftCardService, never()).saveGiftCard(any(GiftCard.class));
        verify(notificationService, never()).sendNotification(anyString(), anyString(), anyString());
    }

    @Test
    void shouldThrowExceptionWhenGiftCardNotFound() {
        when(giftCardService.getGiftCard(anyLong())).thenReturn(null);

        InvalidDataException exception = assertThrows(InvalidDataException.class, () ->
                redeemGiftCardUseCase.execute(1L, 50.0));

        assertEquals("No gift card information found", exception.getMessage());
        verify(giftCardService, never()).saveGiftCard(any(GiftCard.class));
        verify(notificationService, never()).sendNotification(anyString(), anyString(), anyString());
    }

    @Test
    void shouldThrowExceptionWhenGiftCardIsNotActive() {
        GiftCard giftCard = new GiftCard();
        giftCard.setId(1L);
        giftCard.setAmount(50.0);
        giftCard.setStatus(GiftCardStatus.REDEEMED);
        when(giftCardService.getGiftCard(anyLong())).thenReturn(giftCard);

        InvalidDataException exception = assertThrows(InvalidDataException.class, () ->
                redeemGiftCardUseCase.execute(1L, 50.0));

        assertEquals("No gift card information found", exception.getMessage());
        verify(giftCardService, never()).saveGiftCard(any(GiftCard.class));
        verify(notificationService, never()).sendNotification(anyString(), anyString(), anyString());
    }
}

