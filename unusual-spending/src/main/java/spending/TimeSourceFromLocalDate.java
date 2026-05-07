package spending;

import java.time.*;

public class TimeSourceFromLocalDate implements TimeSource {

    private final LocalDate localDate;

    private TimeSourceFromLocalDate(LocalDate localDate) {
        this.localDate = localDate;
    }

    public static TimeSource of(LocalDate localDate) {
        return new TimeSourceFromLocalDate(localDate);
    }

    public static TimeSource of(Month month, Year year) {
        return new TimeSourceFromLocalDate(
                LocalDate.of(year.getValue(), month.getValue(), 1));
    }

    @Override
    public Year currentYear() {
        return Year.from(localDate);
    }

    @Override
    public Month currentMonth() {
        return Month.from(localDate);
    }

    @Override
    public Year yearOfPreviousMonth() {
        var month = currentMonth();
        return month.equals(Month.JANUARY)
                ? currentYear().minusYears(1)
                : currentYear();
    }

    @Override
    public Month previousMonth() {
        return currentMonth().minus(1);
    }
}
