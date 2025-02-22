package co.com.project.application.usecase;

import co.com.project.domain.services.GiftCardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DeleteGiftCardUseCase {

    private final GiftCardService service;

    public void execute(String code) {
        service.deleteGiftCard(code);
    }

}
