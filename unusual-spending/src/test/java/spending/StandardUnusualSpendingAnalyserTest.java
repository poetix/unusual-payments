package spending;

import org.junit.Test;

import java.util.Collections;
import java.util.Map;
import java.util.Set;

import static org.junit.Assert.assertEquals;

public class StandardUnusualSpendingAnalyserTest {

    private static final StandardUnusualSpendingAnalyser unit = new StandardUnusualSpendingAnalyser();

    @Test
    public void ignoresNewPayments() {
        assertEquals(
                UnusualSpendingSummary.empty(),
                unit.analyse(
                    Collections.emptySet(),
                    Set.of(Payment.of(Amount.of(100), Category.TRAVEL))
            ));
    }

    @Test
    public void ignoresPaymentsOfLessThan150PercentMore() {
        assertEquals(
            UnusualSpendingSummary.empty(),
            unit.analyse(
                Set.of(Payment.of(Amount.of(100), Category.TRAVEL)),
                Set.of(Payment.of(Amount.of(120), Category.TRAVEL))
            ));
    }

    @Test
    public void flagsPaymentsOf150PercentMoreThanLastMonth() {
        assertEquals(
                UnusualSpendingSummary.of(Map.of(
                                Category.TRAVEL, Amount.of(150)
                        )),
                unit.analyse(
                        Set.of(Payment.of(Amount.of(100), Category.TRAVEL)),
                        Set.of(
                                Payment.of(Amount.of(120), Category.TRAVEL),
                                Payment.of(Amount.of(30), Category.TRAVEL))
                ));
    }

    @Test
    public void flagsMultipleUnusualSpendsByCategory() {
        assertEquals(
                UnusualSpendingSummary.of(Map.of(
                        Category.TRAVEL, Amount.of(150),
                        Category.GROCERIES, Amount.of(90)
                )),
                unit.analyse(
                        Set.of(Payment.of(Amount.of(50), Category.TRAVEL),
                                Payment.of(Amount.of(50), Category.TRAVEL),
                                Payment.of(Amount.of(50), Category.GROCERIES)),
                        Set.of(Payment.of(Amount.of(150), Category.TRAVEL),
                                Payment.of(Amount.of(40), Category.GROCERIES),
                                Payment.of(Amount.of(50), Category.GROCERIES))
                ));
    }
}
