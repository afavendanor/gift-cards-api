package co.com.project.infraestructure.dataloader;

import co.com.project.domain.model.enums.GiftCardStatus;
import co.com.project.infraestructure.drivenadapters.jpa.giftcards.GiftCardDataRepository;
import co.com.project.infraestructure.drivenadapters.jpa.giftcards.entities.GiftCardData;
import co.com.project.infraestructure.drivenadapters.jpa.user.RoleDataRepository;
import co.com.project.infraestructure.drivenadapters.jpa.user.UserDataRepository;
import co.com.project.infraestructure.drivenadapters.jpa.user.entities.RoleData;
import co.com.project.infraestructure.drivenadapters.jpa.user.entities.UserData;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import java.time.LocalDateTime;
import java.util.List;

@Component
public class DataLoader {

    @Value("${spring.mail.properties.username}")
    private String email;

    @Bean
    CommandLineRunner initDatabase(GiftCardDataRepository giftCardDataRepository, UserDataRepository userDataRepository,
                                   RoleDataRepository roleDataRepository) {
        return args -> {
            RoleData roleAdmin = roleDataRepository.save(new RoleData(null, "ROLE_ADMIN"));
            RoleData roleUser = roleDataRepository.save(new RoleData(null, "ROLE_USER"));
            userDataRepository.save(new UserData(null, "admin", "$2a$10$7fHErmZ2jKHYw059bkgWBes9yYdT3di37i0CtsvQWo9SdTunBnlRe", email, true, List.of(roleAdmin, roleUser)));
            userDataRepository.save(new UserData(null, "user", "$2a$10$/RMNQflQPlJxdAMOvPy8BebOJdFxtGaJwrFJ/TY8229MsWyP5uT4m", email, true, List.of(roleUser)));
            giftCardDataRepository.save(new GiftCardData(null, 100.0, LocalDateTime.now(), LocalDateTime.now().plusDays(25), GiftCardStatus.ACTIVE, 1L, null));
            giftCardDataRepository.save(new GiftCardData(null, 50.0, LocalDateTime.now(), LocalDateTime.now().plusWeeks(7), GiftCardStatus.INACTIVE, 2L, null));
            System.out.println("🎉 Initial data loaded into the database!");
        };
    }
}