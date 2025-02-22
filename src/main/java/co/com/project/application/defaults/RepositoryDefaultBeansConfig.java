package co.com.project.application.defaults;

import co.com.project.domain.gateways.GiftCardRepository;
import co.com.project.domain.model.GiftCard;
import lombok.extern.java.Log;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.util.List;
import java.util.logging.Level;

@Configuration
@Log
public class RepositoryDefaultBeansConfig {

    private final GiftCardRepository giftCardRepository = new GiftCardRepository() {

        @Override
        public GiftCard get(String code) {
            return null;
        }

        @Override
        public List<GiftCard> list() {
            return List.of();
        }

        @Override
        public GiftCard save(GiftCard giftCard) {
            return null;
        }

        @Override
        public void delete(String code) {

        }
    };

    @Bean
    @ConditionalOnMissingBean
    public GiftCardRepository giftCardRepository() {
        alertFakeBean("GiftCardRepository");
        return giftCardRepository;
    }

    private void alertFakeBean(String beanName) {
        log.log(Level.WARNING, "CONFIGURACION FAKE: " + beanName, beanName);
    }

}
