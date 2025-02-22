package co.com.project.infraestructure.drivenadapters.jpa.giftcards.transformer;

import co.com.project.domain.model.GiftCard;
import co.com.project.infraestructure.drivenadapters.jpa.giftcards.entities.GiftCardData;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import java.util.List;

@Mapper
public interface GiftCardDataTransformer {

    GiftCardDataTransformer INSTANCE = Mappers.getMapper(GiftCardDataTransformer.class);

    GiftCard giftCardDataToGiftCard(GiftCardData giftCardData);

    GiftCardData giftCardToGiftCardData(GiftCard giftCard);

    List<GiftCard> giftCardDataListToGiftCardList(List<GiftCardData> giftCardDataList);

}
