package co.com.project.application.usecase;

import co.com.project.domain.exception.InvalidDataException;
import co.com.project.domain.model.GiftCard;
import co.com.project.domain.model.enums.GiftCardStatus;
import co.com.project.domain.services.GiftCardService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UpdateGiftCardUseCase {

    private final GiftCardService service;

    public GiftCard execute(Long id, Double amount, String status) {
        GiftCard giftCard = service.getGiftCard(id);
        if (giftCard != null) {
            if (amount != null && amount > 0) giftCard.setAmount(amount);
            if (!StringUtils.isBlank(status)) giftCard.setStatus(GiftCardStatus.valueOf(status));
            return service.saveGiftCard(giftCard);
        }
        throw new InvalidDataException("No gift card information found");
    }

}
