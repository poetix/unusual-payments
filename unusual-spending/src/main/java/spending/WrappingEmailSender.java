package spending;

public class WrappingEmailSender implements EmailSender {
    @Override
    public void sendEmail(UserId userId, String subject, String body) {
        EmailsUser.email(userId.value(), subject, body);
    }
}
