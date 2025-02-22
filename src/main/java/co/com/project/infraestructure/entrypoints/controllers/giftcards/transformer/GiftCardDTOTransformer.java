package co.com.project.infraestructure.entrypoints.controllers.giftcards.transformer;

import co.com.project.application.dtos.GiftCardDTO;
import co.com.project.domain.model.GiftCard;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface GiftCardDTOTransformer {

    GiftCardDTOTransformer INSTANCE = Mappers.getMapper(GiftCardDTOTransformer.class);

    GiftCard giftCardDTOToGiftCard(GiftCardDTO giftCardDTO);

    GiftCardDTO giftCardToGiftCardDTO(GiftCard giftCard);

    List<GiftCardDTO> giftCardListToGiftCardDTOList(List<GiftCard> giftCardList);

}
