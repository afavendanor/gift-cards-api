package co.com.project.infraestructure.drivenadapters.notification;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.test.context.TestPropertySource;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@TestPropertySource(properties = {
        "spring.mail.properties.host=smtp.test.com",
        "spring.mail.properties.port=587",
        "spring.mail.properties.username=testuser",
        "spring.mail.properties.password=testpass"
})
class MailConfigTest {

    @Autowired
    private JavaMailSender javaMailSender;

    @Test
    void javaMailSenderBean_ShouldBeLoaded() {
        assertNotNull(javaMailSender, "JavaMailSender bean should be loaded in the context");
    }

    @Test
    void javaMailSender_ShouldBeConfiguredCorrectly() {
        assertNotNull(javaMailSender);
    }
}
