package co.com.project.infraestructure.drivenadapters.jpa.giftcards.entities;

import co.com.project.domain.model.enums.GiftCardStatus;
import co.com.project.infraestructure.drivenadapters.jpa.user.entities.UserData;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@RequiredArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity(name = "gift_cards")
public class GiftCardData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Double amount;
    @Column(name = "creation_date")
    private LocalDateTime creationDate;
    @Column(name = "expiration_date")
    private LocalDateTime expirationDate;
    @Enumerated(EnumType.STRING)
    private GiftCardStatus status;
    @Column(name = "user_id")
    private Long userId;
    @ManyToOne
    @JoinColumn(name = "user_id", insertable = false, updatable = false)
    private UserData user;
}
