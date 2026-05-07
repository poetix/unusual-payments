package spending;

import org.junit.Test;
import org.mockito.Mockito;

import java.time.Month;
import java.time.Year;
import java.util.Set;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.contains;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class IntegrationTest {

    private static final Month THIS_MONTH = Month.JANUARY;
    private static final Month LAST_MONTH = Month.DECEMBER;
    private static final Year THIS_YEAR = Year.of(2008);
    private static final Year LAST_YEAR = Year.of(2007);

    private final PaymentFetcher fetcher = Mockito.mock(PaymentFetcher.class);
    private final EmailSender sender = Mockito.mock(EmailSender.class);

    private final TriggersUnusualSpendingEmail unit = new TriggersUnusualSpendingEmail(
            new AnalysingUnusualSpendingFetcher(
                    TimeSourceFromLocalDate.of(THIS_MONTH, THIS_YEAR),
                    fetcher,
                    new StandardUnusualSpendingAnalyser()
            ),
            new FormattingUnusualSpendingEmailSender(
                    sender,
                    new StandardUnusualSpendingEmailFormatter()
            )
    );

    @Test
    public void sendsEmailInExpectedFormatWhenUnusualPaymentsArePresent() {
        when(fetcher.fetchPayments(UserId.of(1), THIS_YEAR, THIS_MONTH))
                .thenReturn(Set.of(
                        Payment.of(Amount.of(100), Category.GROCERIES),
                        Payment.of(Amount.of(150), Category.TRAVEL)));

        when(fetcher.fetchPayments(UserId.of(1), LAST_YEAR, LAST_MONTH))
                .thenReturn(Set.of(
                        Payment.of(Amount.of(80), Category.GROCERIES),
                        Payment.of(Amount.of(100), Category.TRAVEL)));

        unit.trigger(1);

        verify(sender).sendEmail(
                eq(UserId.of(1)),
                anyString(),
                contains("* £150 on travel"));
    }
}
