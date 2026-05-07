package spending;

public class FormattingUnusualSpendingEmailSender implements UnusualSpendingEmailSender {

    private static final String UNUSUAL_SPENDING_SUBJECT = "Unusual spending on your account";

    private final EmailSender emailSender;
    private final UnusualSpendingEmailFormatter unusualSpendingEmailFormatter;

    public FormattingUnusualSpendingEmailSender(EmailSender emailSender, UnusualSpendingEmailFormatter unusualSpendingEmailFormatter) {
        this.emailSender = emailSender;
        this.unusualSpendingEmailFormatter = unusualSpendingEmailFormatter;
    }

    @Override
    public void sendUnusualSpendingEmail(UserId userId, UnusualSpendingSummary unusualSpendingSummary) {
        emailSender.sendEmail(
                userId,
                UNUSUAL_SPENDING_SUBJECT,
                unusualSpendingEmailFormatter.format(unusualSpendingSummary).value());
    }
}
