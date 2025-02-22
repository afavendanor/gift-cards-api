package co.com.project.application.usecase;

import co.com.project.domain.model.GiftCard;
import co.com.project.domain.services.GiftCardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class GettingGiftCardUseCase {

    private final GiftCardService service;

    public GiftCard execute(String code) {
        return service.getGiftCard(code);
    }
}
