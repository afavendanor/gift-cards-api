package co.com.project.application.usecase;

import co.com.project.domain.exception.InvalidDataException;
import co.com.project.domain.model.GiftCard;
import co.com.project.domain.model.User;
import co.com.project.domain.services.GiftCardService;
import co.com.project.domain.services.NotificationService;
import co.com.project.domain.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class CreateGiftCardUseCase {

    private final GiftCardService service;
    private final NotificationService notificationService;
    private final UserService userService;

    public GiftCard execute(GiftCard giftCard) {
        User user = userService.findById(giftCard.getUserId());
        if (user == null) {
            throw new InvalidDataException(String.format("The user id %d is not registered in the system", giftCard.getUserId()));
        }
        if (giftCard.getCreationDate() == null) {
            giftCard.setCreationDate(LocalDateTime.now());
        }
        GiftCard result = service.saveGiftCard(giftCard);
        String subject = "🎁 ¡Tu Gift Card está lista!";
        String body = String.format("¡Felicidades! 🎉\n\n" +
                "Has recibido una Gift Card.\n\n" +
                "🔹 Código: %d\n" +
                "🔹 Monto: $ %.2f\n" +
                "🔹 Fecha de vencimiento: %tF\n\n" +
                "Para redimir tu tarjeta, usa el código en nuestra tienda en línea.\n\n" +
                "¡Gracias por elegirnos!", result.getId(), result.getAmount(), result.getExpirationDate());

        notificationService.sendNotification(user.getEmail(), subject, body);
        return result;
    }

}
