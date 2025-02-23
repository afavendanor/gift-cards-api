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
            throw new InvalidDataException(String.format("El user id %d no està registrado en el sistema", giftCard.getUserId()));
        }
        if (giftCard.getCreationDate() == null) {
            giftCard.setCreationDate(LocalDateTime.now());
        }
        GiftCard result = service.saveGiftCard(giftCard);
        String subject = "Se ha ingresado un nuevo gift card";
        String body = "";

        notificationService.sendNotification(user.getEmail(), subject, body);
        return result;
    }

}
