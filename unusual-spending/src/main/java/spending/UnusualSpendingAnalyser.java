package spending;

import java.util.Set;

public interface UnusualSpendingAnalyser {
    UnusualSpendingSummary analyse(Set<Payment> lastMonthsPayments, Set<Payment> thisMonthsPayments);
}
