package spending;

public interface EmailSender {
    void sendEmail(UserId userId, String subject, String body);
}
