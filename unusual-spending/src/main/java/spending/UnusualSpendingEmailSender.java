package spending;

public interface UnusualSpendingEmailSender {
    void sendUnusualSpendingEmail(UserId userId, UnusualSpendingSummary unusualSpendingSummary);
}
