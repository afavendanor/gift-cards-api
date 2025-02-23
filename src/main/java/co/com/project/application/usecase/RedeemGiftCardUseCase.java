package co.com.project.application.usecase;

import co.com.project.domain.exception.InvalidDataException;
import co.com.project.domain.model.GiftCard;
import co.com.project.domain.model.enums.GiftCardStatus;
import co.com.project.domain.services.GiftCardService;
import co.com.project.domain.services.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class RedeemGiftCardUseCase {

    private final GiftCardService service;
    private final NotificationService notificationService;

    public GiftCard execute(Long id, Double value) {
        GiftCard giftCard = service.getGiftCard(id);
        if (giftCard != null && giftCard.getStatus() == GiftCardStatus.ACTIVE) {
            if (giftCard.getAmount() > value) {
                Double saldo = giftCard.getAmount() - value;
                giftCard.setAmount(saldo);
                notify(id, value, saldo, giftCard.getExpirationDate(), giftCard.getUser().getEmail());
                service.saveGiftCard(giftCard);
                return giftCard;
            } else if (giftCard.getAmount().doubleValue() == value) {
                giftCard.setAmount(0d);
                giftCard.setStatus(GiftCardStatus.REDEEMED);
                service.saveGiftCard(giftCard);
                notify(id, value, 0d, giftCard.getExpirationDate(), giftCard.getUser().getEmail());
                return giftCard;
            } else {
                throw new InvalidDataException("The redeemable value is greater than the balance");
            }
        }
        throw new InvalidDataException("No gift card information found");
    }

    public void notify(Long id, Double value, Double balance, LocalDateTime expirationDate, String email) {
        String subject =  "🎁 ¡Tu Gift Card ha sido redimida!";
        String body = String.format("¡Tu Gift Card ha sido redimida con éxito! 🎉\n\n" +
                "🔹 Código: %d\n" +
                "🔹 Monto utilizado: $ %.2f\n" +
                "🔹 Saldo restante: $ %.2f\n" +
                "🔹 Fecha de vencimiento: %tF\n\n" +
                "Si tienes alguna consulta, no dudes en contactarnos.\n\n" +
                "¡Gracias por tu compra!", id, value,balance, expirationDate);

        notificationService.sendNotification(email, subject, body);
    }

}
