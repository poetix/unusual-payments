package spending;

import java.time.Month;
import java.time.Year;

public interface TimeSource {
    Year currentYear();
    Month currentMonth();
    Year yearOfPreviousMonth();
    Month previousMonth();
}
