package co.com.project.application.usecase;

import co.com.project.domain.exception.InvalidDataException;
import co.com.project.domain.model.GiftCard;
import co.com.project.domain.model.enums.GiftCardStatus;
import co.com.project.domain.services.GiftCardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RedeemGiftCardUseCase {

    private final GiftCardService service;

    public GiftCard execute(String code, Double value) {
        GiftCard giftCard = service.getGiftCard(code);
        if (giftCard != null && giftCard.getStatus() == GiftCardStatus.ACTIVE) {
            if (giftCard.getAmount() > value) {
                Double saldo = giftCard.getAmount() - value;
                giftCard.setAmount(saldo);
                notify(code, value, saldo);
                service.saveGiftCard(giftCard);
                return giftCard;
            } else if (giftCard.getAmount().doubleValue() == value) {
                giftCard.setAmount(0d);
                giftCard.setStatus(GiftCardStatus.REDEEMED);
                service.saveGiftCard(giftCard);
                notify(code, value, 0d);
                return giftCard;
            } else {
                throw new InvalidDataException("The redeemable value is greater than the balance");
            }
        }
        throw new InvalidDataException("No gift card information found");
    }

    public void notify(String code, Double value, Double balance) {
        //Enviar correo
    }

}
