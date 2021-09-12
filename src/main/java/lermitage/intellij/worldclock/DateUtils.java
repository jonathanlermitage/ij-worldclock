package lermitage.intellij.worldclock;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class DateUtils {

    private static final DateTimeFormatter DATETIME_FORMAT = DateTimeFormatter.ofPattern("hh:mm a");

    // https://docs.oracle.com/middleware/12212/wcs/tag-ref/MISC/TimeZones.html
    public static final ZoneId caZoneId = ZoneId.of("America/Montreal");
    public static final ZoneId frZoneId = ZoneId.of("Europe/Paris");

    public static String getDate(ZoneId zoneId) {
        return DATETIME_FORMAT.format(LocalDateTime.now(zoneId));
    }
}
