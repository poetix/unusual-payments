package spending;

public interface UnusualSpendingFetcher {
    UnusualSpendingSummary getUnusualSpending(UserId userId);
}
