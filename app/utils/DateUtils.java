package utils;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

public class DateUtils {
    public static LocalDateTime toLocalDateTime(Timestamp date) {
        return LocalDateTime.ofEpochSecond(date.getTime(), 0, ZoneOffset.UTC);
    }
}
