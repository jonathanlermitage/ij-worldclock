package lermitage.intellij.worldclock;

import com.ibm.icu.util.TimeZone;
import com.intellij.openapi.diagnostic.Logger;
import lermitage.intellij.worldclock.cfg.Defaults;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;

public class DateUtils {

    private static final Logger LOG = Logger.getInstance(DateUtils.class);

    // https://docs.oracle.com/en/java/javase/11/docs/api/java.base/java/time/format/DateTimeFormatter.html
    // Examples:
    // E hh:mm a  -> Tue 08:29 PM
    // EEEE HH:mm -> Tuesday 20:39
    private static final DateTimeFormatter DATETIME_FORMAT = DateTimeFormatter.ofPattern(Defaults.DATE_FORMAT);

    public static String getDate(ZoneId zoneId) {
        return DATETIME_FORMAT.format(LocalDateTime.now(zoneId));
    }

    private static Map<String, String> AVAILABLE_TZ_AND_FLAG = initAllAvailableTZAndFlags();

    private static Map<String, String> initAllAvailableTZAndFlags() {
        AVAILABLE_TZ_AND_FLAG = new LinkedHashMap<>();
        Arrays.stream(Globals.FLAGS).forEach(flag -> {
            try {
                Arrays.stream(TimeZone.getAvailableIDs(flag)).forEach(zoneId -> AVAILABLE_TZ_AND_FLAG.put(zoneId, flag));
            } catch (Exception e) {
                LOG.debug("Failed to get available TZ IDs for flag: " + flag, e);
            }
        });
        return AVAILABLE_TZ_AND_FLAG;
    }

    public static Map<String, String> getAllAvailableTZAndFlags() {
        return AVAILABLE_TZ_AND_FLAG;
    }

    public static String findFlagByTz(String tz) {
        return getAllAvailableTZAndFlags().get(tz);
    }
}
