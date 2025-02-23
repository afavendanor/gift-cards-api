package co.com.project.application.defaults;

import co.com.project.domain.gateways.GiftCardRepository;
import co.com.project.domain.model.GiftCard;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class RepositoryDefaultBeansConfigTest {

    private GiftCardRepository giftCardRepository;

    @BeforeEach
    void setUp() {
        RepositoryDefaultBeansConfig config = new RepositoryDefaultBeansConfig();
        giftCardRepository = config.giftCardRepository();
    }

    @Test
    void testGetGiftCardReturnsNull() {
        assertNull(giftCardRepository.get(1L));
    }

    @Test
    void testListGiftCardsReturnsEmptyList() {
        List<GiftCard> giftCards = giftCardRepository.list();
        assertNotNull(giftCards);
        assertTrue(giftCards.isEmpty());
    }

    @Test
    void testSaveGiftCardReturnsNull() {
        assertNull(giftCardRepository.save(new GiftCard()));
    }

    @Test
    void testDeleteGiftCardDoesNotThrowException() {
        assertDoesNotThrow(() -> giftCardRepository.delete(1L));
    }
}
