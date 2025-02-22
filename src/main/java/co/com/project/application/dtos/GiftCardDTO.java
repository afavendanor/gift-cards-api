package co.com.project.application.dtos;

import co.com.project.domain.model.enums.GiftCardStatus;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import java.time.LocalDateTime;

@RequiredArgsConstructor
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class GiftCardDTO {
    private String code;
    @NotNull(message = "The amount is mandatory")
    @Positive(message = "The amount must be positive")
    private Double amount;
    private LocalDateTime creationDate;
    @NotNull(message = "Expiration date is required")
    private LocalDateTime expirationDate;
    @NotNull(message = "The state is mandatory")
    private GiftCardStatus status;
}
