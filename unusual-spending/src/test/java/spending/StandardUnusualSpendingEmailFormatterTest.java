package spending;

import org.hamcrest.Matchers;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;

public class StandardUnusualSpendingEmailFormatterTest {

    private final UnusualSpendingEmailFormatter unit = new StandardUnusualSpendingEmailFormatter();

    @Test
    public void includesUnusualAmountsAndCategories() {
        assertThat(unit.format(
                UnusualSpendingSummary.of(Map.of(
                        Category.TRAVEL, Amount.of(1000),
                        Category.GROCERIES, Amount.of(new BigDecimal("12.34"))
                ))
        ).value(), Matchers.allOf(
                Matchers.containsString("* £1000 on travel"),
                Matchers.containsString("* £12.34 on groceries")
        ));
    }
}
