package co.com.project.application.usecase;

import co.com.project.domain.model.GiftCard;
import co.com.project.domain.services.GiftCardService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class GettingListGiftCardUseCaseTest {

    private GiftCardService giftCardService;
    private GettingListGiftCardUseCase gettingListGiftCardUseCase;

    @BeforeEach
    void setUp() {
        giftCardService = mock(GiftCardService.class);
        gettingListGiftCardUseCase = new GettingListGiftCardUseCase(giftCardService);
    }

    @Test
    void shouldReturnListOfGiftCardsWhenExists() {
        GiftCard giftCard1 = new GiftCard();
        giftCard1.setId(1L);
        giftCard1.setAmount(50.0);
        GiftCard giftCard2 = new GiftCard();
        giftCard2.setId(2L);
        giftCard2.setAmount(100.0);
        List<GiftCard> mockGiftCards = Arrays.asList(giftCard1, giftCard2);
        when(giftCardService.listGiftCard()).thenReturn(mockGiftCards);

        List<GiftCard> result = gettingListGiftCardUseCase.execute();

        assertNotNull(result);
        assertEquals(2, result.size());
        verify(giftCardService, times(1)).listGiftCard();
    }

    @Test
    void shouldReturnEmptyListWhenNoGiftCardsExist() {
        when(giftCardService.listGiftCard()).thenReturn(Collections.emptyList());

        List<GiftCard> result = gettingListGiftCardUseCase.execute();

        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(giftCardService, times(1)).listGiftCard();
    }
}
