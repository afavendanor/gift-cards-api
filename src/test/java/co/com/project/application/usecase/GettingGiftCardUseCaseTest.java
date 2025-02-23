package co.com.project.application.usecase;

import co.com.project.domain.model.GiftCard;
import co.com.project.domain.services.GiftCardService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class GettingGiftCardUseCaseTest {

    private GiftCardService giftCardService;
    private GettingGiftCardUseCase gettingGiftCardUseCase;

    @BeforeEach
    void setUp() {
        giftCardService = mock(GiftCardService.class);
        gettingGiftCardUseCase = new GettingGiftCardUseCase(giftCardService);
    }

    @Test
    void shouldReturnGiftCardWhenExists() {
        Long giftCardId = 1L;
        GiftCard mockGiftCard = new GiftCard();
        mockGiftCard.setId(giftCardId);
        mockGiftCard.setAmount(100.0);
        when(giftCardService.getGiftCard(anyLong())).thenReturn(mockGiftCard);

        GiftCard result = gettingGiftCardUseCase.execute(giftCardId);

        assertNotNull(result);
        assertEquals(giftCardId, result.getId());
        verify(giftCardService, times(1)).getGiftCard(giftCardId);
    }

    @Test
    void shouldReturnNullWhenGiftCardDoesNotExist() {
        Long giftCardId = 99L;
        when(giftCardService.getGiftCard(anyLong())).thenReturn(null);

        GiftCard result = gettingGiftCardUseCase.execute(giftCardId);

        assertNull(result);
        verify(giftCardService, times(1)).getGiftCard(giftCardId);
    }
}
