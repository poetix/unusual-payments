package spending;

import java.math.BigDecimal;

public record Amount(BigDecimal amountGbp) {

    private static final BigDecimal ONE_AND_A_HALF = new BigDecimal("1.5");

    public static Amount of(int amountGbp) {
        return of(BigDecimal.valueOf(amountGbp));
    }

    public static Amount of(BigDecimal amountGbp) {
        return new Amount(amountGbp);
    }

    public Amount plus(Amount other) {
        return Amount.of(this.amountGbp.add(other.amountGbp));
    }

    public boolean isFiftyPercentMoreThan(Amount other) {
        return amountGbp.compareTo(other.amountGbp.multiply(ONE_AND_A_HALF)) >= 0;
    }
}
