package co.com.project.infraestructure.drivenadapters.notification;

import co.com.project.domain.gateways.NotificationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class NotificationRepositoryAdapterTest {

    private JavaMailSender mailSender;
    private NotificationRepository notificationRepository;

    @BeforeEach
    void setUp() {
        mailSender = Mockito.mock(JavaMailSender.class);
        notificationRepository = new NotificationRepositoryAdapter(mailSender);
    }

    @Test
    void sendEmail_Success() {
        String to = "test@example.com";
        String subject = "Test Subject";
        String body = "Test Body";

        notificationRepository.send(to, subject, body);

        ArgumentCaptor<SimpleMailMessage> captor = ArgumentCaptor.forClass(SimpleMailMessage.class);
        verify(mailSender, times(1)).send(captor.capture());
        SimpleMailMessage sentMessage = captor.getValue();
        assertEquals(to, sentMessage.getTo()[0]);
        assertEquals(subject, sentMessage.getSubject());
        assertEquals(body, sentMessage.getText());
    }
}
