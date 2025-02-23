package co.com.project.infraestructure.entrypoints.controllers;

import co.com.project.application.usecase.*;
import co.com.project.domain.model.GiftCard;
import co.com.project.application.dtos.GiftCardDTO;
import co.com.project.domain.model.enums.GiftCardStatus;
import co.com.project.infraestructure.entrypoints.controllers.giftcards.GiftCardController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.test.context.support.WithMockUser;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(GiftCardController.class)
@Import(GiftCardControllerTest.TestConfig.class)
public class GiftCardControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CreateGiftCardUseCase createGiftCardUseCase;

    @Autowired
    private GettingGiftCardUseCase gettingGiftCardUseCase;

    @Autowired
    private UpdateGiftCardUseCase updateGiftCardUseCase;

    @Autowired
    private DeleteGiftCardUseCase deleteGiftCardUseCase;

    @Autowired
    private RedeemGiftCardUseCase redeemGiftCardUseCase;

    @Autowired
    private GettingListGiftCardUseCase gettingListGiftCardUseCase;

    private GiftCard giftCard;
    private GiftCardDTO giftCardDTO;

    @BeforeEach
    void setUp() {
        giftCard = new GiftCard();
        giftCard.setId(1L);
        giftCard.setAmount(100D);
        giftCard.setUserId(1L);
        giftCard.setStatus(GiftCardStatus.ACTIVE);

        giftCardDTO = new GiftCardDTO();
        giftCardDTO.setId(1L);
        giftCardDTO.setAmount(100D);
        giftCardDTO.setUserId(1L);
        giftCardDTO.setStatus(GiftCardStatus.ACTIVE);
    }

    @Test
    @WithMockUser(username = "testuser", roles = {"USER", "ADMIN"})
    void testGetGiftCard_Success() throws Exception {
        when(gettingGiftCardUseCase.execute(anyLong())).thenReturn(giftCard);

        mockMvc.perform(get("/api/gift-cards/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.amount").value(100.0))
                .andExpect(jsonPath("$.status").value("ACTIVE"));

        verify(gettingGiftCardUseCase, times(1)).execute(1L);
    }

    @Test
    @WithMockUser(username = "testuser", roles = {"USER", "ADMIN"})
    void testGetGiftCardList_Success() throws Exception {
        when(gettingListGiftCardUseCase.execute()).thenReturn(List.of(giftCard));

        mockMvc.perform(get("/api/gift-cards/list"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].amount").value(100.0))
                .andExpect(jsonPath("$[0].status").value("ACTIVE"));

        verify(gettingListGiftCardUseCase, times(1)).execute();
    }

    @Test
    @WithMockUser(username = "testuser", roles = {"ADMIN"})
    void testCreateGiftCard_Success() throws Exception {
        when(createGiftCardUseCase.execute(any())).thenReturn(giftCard);

        mockMvc.perform(put("/api/gift-cards/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"amount\": 100.0,\n" +
                                "  \"creationDate\": null,\n" +
                                "  \"expirationDate\": \"2025-03-21T10:00:00Z\",\n" +
                                "  \"status\": \"ACTIVE\",\n" +
                                "  \"userId\": 2\n" +
                                "}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.amount").value(100.0))
                .andExpect(jsonPath("$.status").value("ACTIVE"));

        verify(createGiftCardUseCase, times(1)).execute(any());
    }

    @Test
    @WithMockUser(username = "testuser", roles = {"ADMIN"})
    void testUpdateGiftCard_Success() throws Exception {
        when(updateGiftCardUseCase.execute(anyLong(), anyDouble(), anyString())).thenReturn(giftCard);

        mockMvc.perform(post("/api/gift-cards/update")
                        .param("id", "1")
                        .param("amount", "150.0")
                        .param("status", "ACTIVE"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.amount").value(100.0))
                .andExpect(jsonPath("$.status").value("ACTIVE"));

        verify(updateGiftCardUseCase, times(1)).execute(anyLong(), anyDouble(), anyString());
    }

    @Test
    @WithMockUser(username = "testuser", roles = {"ADMIN", "USER"})
    void testRedimeGiftCard_Success() throws Exception {
        when(redeemGiftCardUseCase.execute(anyLong(), anyDouble())).thenReturn(giftCard);

        mockMvc.perform(post("/api/gift-cards/redeem")
                        .param("id", "1")
                        .param("value", "150.0"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.amount").value(100.0))
                .andExpect(jsonPath("$.status").value("ACTIVE"));

        verify(redeemGiftCardUseCase, times(1)).execute(anyLong(), anyDouble());
    }

    @Test
    @WithMockUser(username = "testuser", roles = {"ADMIN"})
    void testDeleteGiftCard_Success() throws Exception {
        doNothing().when(deleteGiftCardUseCase).execute(anyLong());

        mockMvc.perform(delete("/api/gift-cards/1"))
                .andExpect(status().isOk());

        verify(deleteGiftCardUseCase, times(1)).execute(1L);
    }

    @TestConfiguration
    static class TestConfig {

        @Bean
        public SecurityFilterChain testSecurityFilterChain(HttpSecurity http) throws Exception {
            http.csrf(csrf -> csrf.disable())
                    .authorizeHttpRequests(auth -> auth.anyRequest().permitAll());
            return http.build();
        }

        @Bean
        CreateGiftCardUseCase createGiftCardUseCase() {
            return Mockito.mock(CreateGiftCardUseCase.class);
        }

        @Bean
        GettingGiftCardUseCase gettingGiftCardUseCase() {
            return Mockito.mock(GettingGiftCardUseCase.class);
        }

        @Bean
        UpdateGiftCardUseCase updateGiftCardUseCase() {
            return Mockito.mock(UpdateGiftCardUseCase.class);
        }

        @Bean
        DeleteGiftCardUseCase deleteGiftCardUseCase() {
            return Mockito.mock(DeleteGiftCardUseCase.class);
        }

        @Bean
        RedeemGiftCardUseCase redeemGiftCardUseCase() {
            return Mockito.mock(RedeemGiftCardUseCase.class);
        }

        @Bean
        GettingListGiftCardUseCase gettingListGiftCardUseCase() {
            return Mockito.mock(GettingListGiftCardUseCase.class);
        }
    }
}
