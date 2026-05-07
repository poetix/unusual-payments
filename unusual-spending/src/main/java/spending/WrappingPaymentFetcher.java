package spending;

import java.time.Month;
import java.time.Year;
import java.util.Set;

public class WrappingPaymentFetcher implements PaymentFetcher {

    private final FetchesUserPaymentsByMonth<Payment> wrapped;

    public WrappingPaymentFetcher(FetchesUserPaymentsByMonth<Payment> wrapped) {
        this.wrapped = wrapped;
    }

    @Override
    public Set<Payment> fetchPayments(UserId userId, Year year, Month month) {
        return wrapped.fetch(userId.value(), year.getValue(), month.getValue());
    }
}
