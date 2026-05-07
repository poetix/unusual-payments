package spending;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public record UnusualSpendingSummary(
        Map<Category, Amount> unusualSpending
) {

    public static UnusualSpendingSummary empty() {
        return new UnusualSpendingSummary(Collections.emptyMap());
    }

    public static UnusualSpendingSummary of(Map<Category, Amount> unusualSpending) {
        return new UnusualSpendingSummary(unusualSpending);
    }

    public boolean isNotEmpty() {
        return !unusualSpending.isEmpty();
    }
}
