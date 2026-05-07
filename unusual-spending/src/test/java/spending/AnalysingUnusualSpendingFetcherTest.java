package spending;

import org.junit.Test;
import org.mockito.Mockito;

import java.time.Month;
import java.time.Year;
import java.util.Map;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

public class AnalysingUnusualSpendingFetcherTest {

    private static final Month THIS_MONTH = Month.JANUARY;
    private static final Month LAST_MONTH = Month.DECEMBER;
    private static final Year THIS_YEAR = Year.of(2008);
    private static final Year LAST_YEAR = Year.of(2007);
    private final TimeSource timeSource = TimeSourceFromLocalDate.of(THIS_MONTH, THIS_YEAR);

    private final PaymentFetcher paymentFetcher = Mockito.mock(PaymentFetcher.class);
    private final UnusualSpendingAnalyser analyser = Mockito.mock(UnusualSpendingAnalyser.class);

    private final AnalysingUnusualSpendingFetcher unit = new AnalysingUnusualSpendingFetcher(
            timeSource,
            paymentFetcher,
            analyser
    );

    @Test
    public void analysesPaymentsForCurrentAndPreviousMonths() {
        UserId userId = UserId.of(1);

        var lastMonthsPayments = Set.of(Payment.of(Amount.of(123), Category.TRAVEL));
        var thisMonthsPayments = Set.of(Payment.of(Amount.of(456), Category.TRAVEL));

        when(paymentFetcher.fetchPayments(userId, LAST_YEAR, LAST_MONTH))
                .thenReturn(lastMonthsPayments);
        when(paymentFetcher.fetchPayments(userId, THIS_YEAR, THIS_MONTH))
                .thenReturn(thisMonthsPayments);

        UnusualSpendingSummary expectedSpendingSummary = UnusualSpendingSummary.of(Map.of(
                Category.TRAVEL, Amount.of(456)
        ));

        when(analyser.analyse(lastMonthsPayments, thisMonthsPayments))
                .thenReturn(expectedSpendingSummary);

        var unusualSpending = unit.getUnusualSpending(userId);

        assertEquals(expectedSpendingSummary, unusualSpending);
    }
}
