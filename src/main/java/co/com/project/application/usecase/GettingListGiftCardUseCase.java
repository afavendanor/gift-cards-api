package co.com.project.application.usecase;

import co.com.project.domain.model.GiftCard;
import co.com.project.domain.services.GiftCardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
@RequiredArgsConstructor
public class GettingListGiftCardUseCase {

    private final GiftCardService service;

    public List<GiftCard> execute() {
        return service.listGiftCard();
    }
}
