package co.com.project.domain.services;

import co.com.project.domain.gateways.NotificationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.*;

class NotificationServiceTest {

    @Mock
    private NotificationRepository notificationRepository;

    @InjectMocks
    private NotificationService notificationService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSendNotification() {
        String to = "test@example.com";
        String subject = "Test Subject";
        String body = "Test Body";

        notificationService.sendNotification(to, subject, body);

        verify(notificationRepository, times(1)).send(to, subject, body);
    }
}
