package co.com.project.domain.gateways;

import co.com.project.domain.model.GiftCard;
import java.util.List;

public interface GiftCardRepository {

    GiftCard get(String code);

    List<GiftCard> list();

    GiftCard save(GiftCard giftCard);

    void delete(String code);

}
