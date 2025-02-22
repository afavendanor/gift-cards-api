package co.com.project.infraestructure.entrypoints.controllers.giftcards;


import co.com.project.application.usecase.*;
import co.com.project.domain.model.GiftCard;
import co.com.project.application.dtos.GiftCardDTO;
import co.com.project.infraestructure.entrypoints.controllers.giftcards.transformer.GiftCardDTOTransformer;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/gift-cards")
@RequiredArgsConstructor
@Tag(name = "GiftCard")
public class GiftCardController {

    private final CreateGiftCardUseCase createGiftCardUseCase;
    private final GettingGiftCardUseCase gettingGiftCardUseCase;
    private final UpdateGiftCardUseCase updateGiftCardUseCase;
    private final DeleteGiftCardUseCase deleteGiftCardUseCase;
    private final RedeemGiftCardUseCase redeemGiftCardUseCase;
    private final GettingListGiftCardUseCase gettingListGiftCardUseCase;

    @GetMapping("/{code}")
    @Operation(summary = "Return gift card information")
    public ResponseEntity<GiftCardDTO> get(@PathVariable String code) {
        GiftCard giftCard = gettingGiftCardUseCase.execute(code);
        return ResponseEntity.ok(GiftCardDTOTransformer.INSTANCE.giftCardToGiftCardDTO(giftCard));
    }

    @GetMapping("/list")
    @Operation(summary = "Return list of gift card information")
    public ResponseEntity<List<GiftCardDTO>> list() {
        List<GiftCard> giftCardList = gettingListGiftCardUseCase.execute();
        return ResponseEntity.ok(GiftCardDTOTransformer.INSTANCE.giftCardListToGiftCardDTOList(giftCardList));
    }

    @PutMapping("/create")
    @Operation(summary = "Save information of a gift card")
    public ResponseEntity<?> create(@Valid @RequestBody GiftCardDTO request) {
        GiftCard giftCard = this.createGiftCardUseCase.execute(GiftCardDTOTransformer.INSTANCE.giftCardDTOToGiftCard(request));
        return ResponseEntity.ok(GiftCardDTOTransformer.INSTANCE.giftCardToGiftCardDTO(giftCard));
    }

    @PostMapping("/update")
    @Operation(summary = "Update information of a gift card")
    public ResponseEntity<GiftCardDTO> update(@RequestParam String code,
                                              @RequestParam(required = false) Double amount,
                                              @RequestParam(required = false) String status) {
        GiftCard giftCard = this.updateGiftCardUseCase.execute(code, amount, status);
        return ResponseEntity.ok(GiftCardDTOTransformer.INSTANCE.giftCardToGiftCardDTO(giftCard));
    }

    @PostMapping("/redeem")
    @Operation(summary = "Redeem information of a gift card")
    public ResponseEntity<GiftCardDTO> redeem(@RequestParam String code, @RequestParam Double value) {
        GiftCard giftCard = this.redeemGiftCardUseCase.execute(code, value);
        return ResponseEntity.ok(GiftCardDTOTransformer.INSTANCE.giftCardToGiftCardDTO(giftCard));
    }

    @DeleteMapping("/{code}")
    @Operation(summary = "Delete information of a gift card")
    public ResponseEntity<Void> delete(@PathVariable String code) {
        this.deleteGiftCardUseCase.execute(code);
        return ResponseEntity.ok().build();
    }

}
