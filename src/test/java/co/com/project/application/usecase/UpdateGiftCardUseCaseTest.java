package co.com.project.application.usecase;

import co.com.project.domain.exception.InvalidDataException;
import co.com.project.domain.model.GiftCard;
import co.com.project.domain.model.enums.GiftCardStatus;
import co.com.project.domain.services.GiftCardService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UpdateGiftCardUseCaseTest {

    private GiftCardService giftCardService;
    private UpdateGiftCardUseCase updateGiftCardUseCase;

    @BeforeEach
    void setUp() {
        giftCardService = mock(GiftCardService.class);
        updateGiftCardUseCase = new UpdateGiftCardUseCase(giftCardService);
    }

    @Test
    void shouldUpdateGiftCardAmount() {
        GiftCard giftCard = new GiftCard();
        giftCard.setId(1L);
        giftCard.setAmount(100.0);
        giftCard.setStatus(GiftCardStatus.ACTIVE);
        when(giftCardService.getGiftCard(anyLong())).thenReturn(giftCard);
        when(giftCardService.saveGiftCard(any(GiftCard.class))).thenReturn(giftCard);

        GiftCard updatedGiftCard = updateGiftCardUseCase.execute(1L, 200.0, null);

        assertEquals(200.0, updatedGiftCard.getAmount());
        assertEquals(GiftCardStatus.ACTIVE, updatedGiftCard.getStatus());
        verify(giftCardService, times(1)).saveGiftCard(any(GiftCard.class));
    }

    @Test
    void shouldUpdateGiftCardStatus() {
        GiftCard giftCard = new GiftCard();
        giftCard.setId(1L);
        giftCard.setAmount(100.0);
        giftCard.setStatus(GiftCardStatus.ACTIVE);
        when(giftCardService.getGiftCard(anyLong())).thenReturn(giftCard);
        when(giftCardService.saveGiftCard(any(GiftCard.class))).thenReturn(giftCard);

        GiftCard updatedGiftCard = updateGiftCardUseCase.execute(1L, null, "REDEEMED");

        assertEquals(100.0, updatedGiftCard.getAmount());
        assertEquals(GiftCardStatus.REDEEMED, updatedGiftCard.getStatus());
        verify(giftCardService, times(1)).saveGiftCard(any(GiftCard.class));
    }

    @Test
    void shouldUpdateGiftCardAmountAndStatus() {
        GiftCard giftCard = new GiftCard();
        giftCard.setId(1L);
        giftCard.setAmount(100.0);
        giftCard.setStatus(GiftCardStatus.ACTIVE);
        when(giftCardService.getGiftCard(anyLong())).thenReturn(giftCard);
        when(giftCardService.saveGiftCard(any(GiftCard.class))).thenReturn(giftCard);

        GiftCard updatedGiftCard = updateGiftCardUseCase.execute(1L, 150.0, "REDEEMED");

        assertEquals(150.0, updatedGiftCard.getAmount());
        assertEquals(GiftCardStatus.REDEEMED, updatedGiftCard.getStatus());
        verify(giftCardService, times(1)).saveGiftCard(any(GiftCard.class));
    }

    @Test
    void shouldNotUpdateWhenBothValuesAreNull() {
        GiftCard giftCard = new GiftCard();
        giftCard.setId(1L);
        giftCard.setAmount(100.0);
        giftCard.setStatus(GiftCardStatus.ACTIVE);
        when(giftCardService.getGiftCard(anyLong())).thenReturn(giftCard);
        when(giftCardService.saveGiftCard(any(GiftCard.class))).thenReturn(giftCard);

        GiftCard updatedGiftCard = updateGiftCardUseCase.execute(1L, null, null);

        assertEquals(100.0, updatedGiftCard.getAmount());
        assertEquals(GiftCardStatus.ACTIVE, updatedGiftCard.getStatus());
        verify(giftCardService, times(1)).saveGiftCard(any(GiftCard.class));
    }

    @Test
    void shouldThrowExceptionWhenGiftCardNotFound() {
        when(giftCardService.getGiftCard(anyLong())).thenReturn(null);

        InvalidDataException exception = assertThrows(InvalidDataException.class, () ->
                updateGiftCardUseCase.execute(1L, 200.0, "REDEEMED"));

        assertEquals("No gift card information found", exception.getMessage());
        verify(giftCardService, never()).saveGiftCard(any(GiftCard.class));
    }
}
