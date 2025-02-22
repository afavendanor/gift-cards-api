package co.com.project.infraestructure.drivenadapters.jpa.giftcards;

import co.com.project.infraestructure.drivenadapters.jpa.giftcards.entities.GiftCardData;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GiftCardDataRepository extends JpaRepository<GiftCardData, String> {
}
