package spending;

public class TriggersUnusualSpendingEmail {

	private final UnusualSpendingFetcher unusualSpendingFetcher;
	private final UnusualSpendingEmailSender unusualSpendingEmailSender;

    public TriggersUnusualSpendingEmail(UnusualSpendingFetcher unusualSpendingFetcher, UnusualSpendingEmailSender unusualSpendingEmailSender) {
		this.unusualSpendingFetcher = unusualSpendingFetcher;
        this.unusualSpendingEmailSender = unusualSpendingEmailSender;
    }

    public void trigger(long userId) {
		UserId id = UserId.of(userId);
		UnusualSpendingSummary unusualSpending = unusualSpendingFetcher.getUnusualSpending(id);
		if (unusualSpending.isNotEmpty()) {
			unusualSpendingEmailSender.sendUnusualSpendingEmail(id, unusualSpending);
		}
	}

}
