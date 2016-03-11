package utils;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;

public class DateUtils {
    public static LocalDateTime toLocalDateTime(Date date) {
        return LocalDateTime.ofEpochSecond(date.getTime(), 0, ZoneOffset.UTC);
    }
}
