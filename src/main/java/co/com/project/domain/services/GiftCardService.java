package co.com.project.domain.services;

import co.com.project.domain.gateways.GiftCardRepository;
import co.com.project.domain.model.GiftCard;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class GiftCardService {

    private final GiftCardRepository repository;

    public GiftCard getGiftCard(String code) {
        return repository.get(code);
    }

    public List<GiftCard> listGiftCard() {
        return repository.list();
    }

    public GiftCard saveGiftCard(GiftCard giftCard) {
        return repository.save(giftCard);
    }

    public void deleteGiftCard(String code) {
        repository.delete(code);
    }
}
