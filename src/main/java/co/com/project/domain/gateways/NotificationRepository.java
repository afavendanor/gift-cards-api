package co.com.project.domain.gateways;

public interface NotificationRepository {

    void send(String to, String subject, String body);

}
