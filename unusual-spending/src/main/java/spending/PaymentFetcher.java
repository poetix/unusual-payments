package spending;

import java.time.Month;
import java.time.Year;
import java.util.Set;

public interface PaymentFetcher {
    Set<Payment> fetchPayments(UserId userId, Year year, Month month);
}
