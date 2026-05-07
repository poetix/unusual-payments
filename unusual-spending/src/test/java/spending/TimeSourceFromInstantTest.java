package spending;

import org.junit.Test;

import java.time.Month;
import java.time.Year;

import static org.junit.Assert.assertEquals;

public class TimeSourceFromInstantTest {

    @Test
    public void previousMonthOfJanuaryIsDecemberOfPreviousYear() {
        TimeSource timeSource = TimeSourceFromLocalDate.of(Month.JANUARY, Year.of(1963));

        assertEquals(Month.DECEMBER, timeSource.previousMonth());
        assertEquals(Year.of(1962), timeSource.yearOfPreviousMonth());
    }
}
