package co.com.project.infraestructure.drivenadapters.jpa.giftcards;

import co.com.project.domain.gateways.GiftCardRepository;
import co.com.project.domain.model.GiftCard;
import co.com.project.infraestructure.drivenadapters.jpa.giftcards.transformer.GiftCardDataTransformer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@RequiredArgsConstructor
@Repository
public class GiftCardRepositoryAdapter implements GiftCardRepository {

    private final GiftCardDataRepository repository;

    @Override
    @Transactional(readOnly = true)
    public GiftCard get(String code) {
        return GiftCardDataTransformer.INSTANCE.giftCardDataToGiftCard(
                repository.findById(code).orElse(null)
        );
    }

    @Override
    @Transactional(readOnly = true)
    public List<GiftCard> list() {
        return GiftCardDataTransformer.INSTANCE.giftCardDataListToGiftCardList(
                repository.findAll()
        );
    }

    @Override
    @Transactional
    public GiftCard save(GiftCard giftCard) {
        repository.save(GiftCardDataTransformer.INSTANCE.giftCardToGiftCardData(giftCard));
        return giftCard;
    }

    @Override
    @Transactional
    public void delete(String code) {
        repository.deleteById(code);
    }
}
