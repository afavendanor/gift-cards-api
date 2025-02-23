package co.com.project.domain.model;

import co.com.project.domain.model.enums.GiftCardStatus;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import java.time.LocalDateTime;

@RequiredArgsConstructor
@Getter
@Setter
public class GiftCard {
    private Long id;
    private Double amount;
    private LocalDateTime creationDate;
    private LocalDateTime expirationDate;
    private GiftCardStatus status;
    private Long userId;
    private User user;
}
