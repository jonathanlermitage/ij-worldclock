package lermitage.intellij.worldclock;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.time.ZoneId;
import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class DateUtilsTest {

    private static final Set<String> JVM_AVAILABLE_ZONE_IDS = ZoneId.getAvailableZoneIds();

    private static Stream<Arguments> providerAllAvailableTZWithFlags() {
        Map<String, String> allAvailableTZAndFlags = DateUtils.getAllAvailableTZAndFlags();
        Stream.Builder<Arguments> dataStream = Stream.builder();
        for (String key : allAvailableTZAndFlags.keySet()) {
            if (!key.startsWith("GMT") && !key.equals("UTC") && !key.equals("CST") && !key.equals("EST") && !key.equals("PST")) {
                dataStream.add(Arguments.of(key, allAvailableTZAndFlags.get(key)));
            }
        }
        return dataStream.build();
    }

    @Test
    void getAllAvailableTZAndFlags_should_not_be_empty() {
        assertFalse(DateUtils.getAllAvailableTZAndFlags().isEmpty());
    }

    @ParameterizedTest
    @MethodSource("providerAllAvailableTZWithFlags")
    void timezone_should_be_supported_by_jvm(String tz, String flag) {
        assertNotNull(tz);
        assertNotNull(flag);
        assertTrue(JVM_AVAILABLE_ZONE_IDS.contains(tz));
    }

    @ParameterizedTest
    @MethodSource("providerAllAvailableTZWithFlags")
    void getDate_12h_should_work_with_tz(String tz) {
        assertTrue(DateUtils.getDate(ZoneId.of(tz), false).length() > 0);
    }

    @ParameterizedTest
    @MethodSource("providerAllAvailableTZWithFlags")
    void getDate_24h_should_work_with_tz(String tz) {
        assertTrue(DateUtils.getDate(ZoneId.of(tz), true).length() > 0);
    }

    @ParameterizedTest
    @MethodSource("providerAllAvailableTZWithFlags")
    void findFlagByTz_should_work_with_tz(String tz) {
        assertTrue(DateUtils.findFlagByTz(tz).length() > 0);
    }
}
