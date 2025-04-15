package Api_Request;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DatePicker {

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public static String getIncrementedDate(int daysToAdd) {
        return LocalDate.now().plusDays(daysToAdd).format(formatter);
    }
}
