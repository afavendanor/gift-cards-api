package co.com.project.domain.services;

import co.com.project.domain.gateways.NotificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NotificationService {

    private final NotificationRepository notificationRepository;

    public void sendNotification(String to, String subject, String body) {
        notificationRepository.send(to, subject, body);
    }

}
