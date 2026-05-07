package spending;

import java.util.Set;

public class AnalysingUnusualSpendingFetcher implements UnusualSpendingFetcher {

    private final TimeSource timeSource;
    private final PaymentFetcher paymentFetcher;
    private final UnusualSpendingAnalyser paymentAnalyser;

    public AnalysingUnusualSpendingFetcher(TimeSource timeSource, PaymentFetcher paymentFetcher, UnusualSpendingAnalyser paymentAnalyser) {
        this.timeSource = timeSource;
        this.paymentFetcher = paymentFetcher;
        this.paymentAnalyser = paymentAnalyser;
    }

    @Override
    public UnusualSpendingSummary getUnusualSpending(UserId userId) {
        Set<Payment> thisMonthsPayments = paymentFetcher.fetchPayments(
                userId,
                timeSource.currentYear(),
                timeSource.currentMonth()
        );

        Set<Payment> lastMonthsPayments = paymentFetcher.fetchPayments(
                userId,
                timeSource.yearOfPreviousMonth(),
                timeSource.previousMonth()
        );

        return paymentAnalyser.analyse(lastMonthsPayments, thisMonthsPayments);
    }
}
