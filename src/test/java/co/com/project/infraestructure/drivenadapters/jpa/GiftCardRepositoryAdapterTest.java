package co.com.project.infraestructure.drivenadapters.jpa;

import co.com.project.domain.model.GiftCard;
import co.com.project.domain.model.enums.GiftCardStatus;
import co.com.project.infraestructure.drivenadapters.jpa.giftcards.GiftCardDataRepository;
import co.com.project.infraestructure.drivenadapters.jpa.giftcards.GiftCardRepositoryAdapter;
import co.com.project.infraestructure.drivenadapters.jpa.giftcards.entities.GiftCardData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class GiftCardRepositoryAdapterTest {

    @Mock
    private GiftCardDataRepository giftCardDataRepository;

    @InjectMocks
    private GiftCardRepositoryAdapter giftCardRepositoryAdapter;

    GiftCardData giftCardData;
    GiftCard giftCard;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        giftCardData = new GiftCardData();
        giftCardData.setId(1L);
        giftCardData.setAmount(100D);
        giftCardData.setExpirationDate(LocalDateTime.now().plusDays(30));
        giftCardData.setStatus(GiftCardStatus.ACTIVE);
        giftCardData.setUserId(1L);

        giftCard = new GiftCard();
        giftCard.setId(1L);
        giftCard.setAmount(100D);
        giftCard.setExpirationDate(LocalDateTime.now().plusDays(30));
        giftCard.setStatus(GiftCardStatus.ACTIVE);
        giftCard.setUserId(1L);
    }

    @Test
    void testGetGiftCard() {
        when(giftCardDataRepository.findById(anyLong())).thenReturn(Optional.of(giftCardData));

        GiftCard result = giftCardRepositoryAdapter.get(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals(100.0, result.getAmount());
        verify(giftCardDataRepository, times(1)).findById(1L);
    }

    @Test
    void testGetGiftCard_NotFound() {
        when(giftCardDataRepository.findById(anyLong())).thenReturn(Optional.empty());

        GiftCard result = giftCardRepositoryAdapter.get(anyLong());

        assertNull(result);
        verify(giftCardDataRepository, times(1)).findById(anyLong());
    }

    @Test
    void testListGiftCards() {
        when(giftCardDataRepository.findAll()).thenReturn(List.of(giftCardData, giftCardData));

        List<GiftCard> result = giftCardRepositoryAdapter.list();

        assertEquals(2, result.size());
        assertEquals(1L, result.get(0).getId());
        assertEquals(100.0, result.get(1).getAmount());
        verify(giftCardDataRepository, times(1)).findAll();
    }

    @Test
    void testSaveGiftCard() {
        when(giftCardDataRepository.save(any(GiftCardData.class))).thenReturn(giftCardData);

        GiftCard result = giftCardRepositoryAdapter.save(giftCard);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals(100.0, result.getAmount());
        verify(giftCardDataRepository, times(1)).save(any(GiftCardData.class));
    }

    @Test
    void testDeleteGiftCard() {
        doNothing().when(giftCardDataRepository).deleteById(anyLong());

        giftCardRepositoryAdapter.delete(1L);

        verify(giftCardDataRepository, times(1)).deleteById(1L);
    }
}
