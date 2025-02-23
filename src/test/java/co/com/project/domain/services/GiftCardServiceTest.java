package co.com.project.domain.services;

import co.com.project.domain.gateways.GiftCardRepository;
import co.com.project.domain.model.GiftCard;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class GiftCardServiceTest {

    @Mock
    private GiftCardRepository repository;

    @InjectMocks
    private GiftCardService service;

    private GiftCard giftCard;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        giftCard = new GiftCard();
        giftCard.setId(1L);
        giftCard.setAmount(100.0);
    }

    @Test
    void testGetGiftCard() {
        when(repository.get(anyLong())).thenReturn(giftCard);
        GiftCard result = service.getGiftCard(1L);
        assertNotNull(result);
        assertEquals(1L, result.getId());
    }

    @Test
    void testListGiftCard() {
        when(repository.list()).thenReturn(List.of(giftCard));
        List<GiftCard> result = service.listGiftCard();
        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
    }

    @Test
    void testSaveGiftCard() {
        when(repository.save(any(GiftCard.class))).thenReturn(giftCard);
        GiftCard result = service.saveGiftCard(giftCard);
        assertNotNull(result);
        assertEquals(100.0, result.getAmount());
    }

    @Test
    void testDeleteGiftCard() {
        doNothing().when(repository).delete(anyLong());
        service.deleteGiftCard(1L);
        verify(repository, times(1)).delete(1L);
    }
}
