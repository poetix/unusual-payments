package spending;

import java.math.BigDecimal;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class StandardUnusualSpendingAnalyser implements UnusualSpendingAnalyser {

    @Override
    public UnusualSpendingSummary analyse(Set<Payment> lastMonthsPayments, Set<Payment> thisMonthsPayments) {
        var lastMonthsPaymentsByCategory = sumByCategory(lastMonthsPayments);
        var thisMonthsPaymentsByCategory = sumByCategory(thisMonthsPayments);

        var unusual = thisMonthsPaymentsByCategory.entrySet().stream()
                .filter(entry -> {
                            var lastMonthsPayment = lastMonthsPaymentsByCategory.get(entry.getKey());

                            return lastMonthsPayment != null &&
                                    entry.getValue().isFiftyPercentMoreThan(lastMonthsPayment);
                        })
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

        return UnusualSpendingSummary.of(unusual);
    }

    private Map<Category, Amount> sumByCategory(Set<Payment> payments) {
        return payments.stream().collect(
                Collectors.groupingBy(
                        Payment::category,
                        Collectors.reducing(
                                Amount.of(BigDecimal.ZERO),
                                Payment::amount,
                                Amount::plus
                        )));
    }
}
