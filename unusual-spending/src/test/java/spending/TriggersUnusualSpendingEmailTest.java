package spending;

import org.junit.Test;
import org.mockito.Mockito;

import java.util.Map;

public class TriggersUnusualSpendingEmailTest {

    private final UnusualSpendingFetcher unusualSpendingFetcher = Mockito.mock(UnusualSpendingFetcher.class);
    private final UnusualSpendingEmailSender unusualSpendingEmailSender = Mockito.mock(UnusualSpendingEmailSender.class);
    private final TriggersUnusualSpendingEmail unit = new TriggersUnusualSpendingEmail(
            unusualSpendingFetcher,
            unusualSpendingEmailSender
    );

    @Test
    public void sendsNoEmailIfNoUnusualPaymentsFound() {
        Mockito.when(unusualSpendingFetcher.getUnusualSpending(UserId.of(1)))
                        .thenReturn(UnusualSpendingSummary.empty());

        unit.trigger(1);
        
        Mockito.verifyNoInteractions(unusualSpendingEmailSender);
    }

    @Test
    public void sendEmailIfUnusualPaymentsFound() {
        var userId = UserId.of(1);
        var unusualSpending = UnusualSpendingSummary.of(Map.of(
                Category.GROCERIES, Amount.of(123)
        ));

        Mockito.when(unusualSpendingFetcher.getUnusualSpending(UserId.of(1)))
                .thenReturn(unusualSpending);

        unit.trigger(userId.value());

        Mockito.verify(unusualSpendingEmailSender).sendUnusualSpendingEmail(userId, unusualSpending);
    }
}
