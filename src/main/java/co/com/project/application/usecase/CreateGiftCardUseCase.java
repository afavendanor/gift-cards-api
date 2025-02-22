package co.com.project.application.usecase;

import co.com.project.domain.model.GiftCard;
import co.com.project.domain.services.GiftCardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import java.time.LocalDateTime;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class CreateGiftCardUseCase {

    private final GiftCardService service;

    public GiftCard execute(GiftCard giftCard) {
        giftCard.setCode(UUID.randomUUID().toString());
        if (giftCard.getCreationDate() == null) {
            giftCard.setCreationDate(LocalDateTime.now());
        }
        return service.saveGiftCard(giftCard);
    }

}
