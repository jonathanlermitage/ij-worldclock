package lermitage.intellij.worldclock;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class DateUtils {

    // https://docs.oracle.com/en/java/javase/11/docs/api/java.base/java/time/format/DateTimeFormatter.html
    // Examples:
    // E hh:mm a  -> Tue 08:29 PM
    // EEEE HH:mm -> Tuesday 20:39
    private static final DateTimeFormatter DATETIME_FORMAT = DateTimeFormatter.ofPattern("E hh:mm a");

    // https://docs.oracle.com/middleware/12212/wcs/tag-ref/MISC/TimeZones.html
    public static final ZoneId caZoneId = ZoneId.of("America/Montreal");
    public static final ZoneId frZoneId = ZoneId.of("Europe/Paris");

    public static String getDate(ZoneId zoneId) {
        return DATETIME_FORMAT.format(LocalDateTime.now(zoneId));
    }
}
