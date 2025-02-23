package co.com.project.application.usecase;

import co.com.project.domain.services.GiftCardService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;


public class DeleteGiftCardUseCaseTest {

    private GiftCardService giftCardService;
    private DeleteGiftCardUseCase deleteGiftCardUseCase;

    @BeforeEach
    void setUp() {
        giftCardService = mock(GiftCardService.class);
        deleteGiftCardUseCase = new DeleteGiftCardUseCase(giftCardService);
    }

    @Test
    public void shouldDeleteGiftCardSuccessfully() {
        Long giftCardId = 1L;

        deleteGiftCardUseCase.execute(giftCardId);

        verify(giftCardService, times(1)).deleteGiftCard(giftCardId);
    }

    @Test
    public void shouldHandleNonExistentGiftCard() {
        Long nonExistentId = 99L;
        doThrow(new RuntimeException("Gift Card not found")).when(giftCardService).deleteGiftCard(anyLong());

        try {
            deleteGiftCardUseCase.execute(nonExistentId);
        } catch (RuntimeException e) {
            assert e.getMessage().equals("Gift Card not found");
        }

        verify(giftCardService, times(1)).deleteGiftCard(nonExistentId);
    }
}

