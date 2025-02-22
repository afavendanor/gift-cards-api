package co.com.project.infraestructure.drivenadapters.jpa.giftcards.entities;

import co.com.project.domain.model.enums.GiftCardStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import java.time.LocalDateTime;

@RequiredArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity(name = "gift_cards")
public class GiftCardData {
    @Id
    private String code;
    private Double amount;
    @Column(name = "creation_date")
    private LocalDateTime creationDate;
    @Column(name = "expiration_date")
    private LocalDateTime expirationDate;
    @Enumerated(EnumType.STRING)
    private GiftCardStatus status;

}
