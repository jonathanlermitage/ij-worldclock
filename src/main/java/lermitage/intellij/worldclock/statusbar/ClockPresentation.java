package lermitage.intellij.worldclock.statusbar;

import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.ui.popup.ListPopup;
import com.intellij.openapi.util.IconLoader;
import com.intellij.openapi.wm.StatusBarWidget;
import com.intellij.util.Consumer;
import lermitage.intellij.worldclock.DateUtils;
import lermitage.intellij.worldclock.Globals;
import lermitage.intellij.worldclock.cfg.SettingsService;
import org.jetbrains.annotations.Nullable;

import javax.swing.Icon;
import java.awt.event.MouseEvent;
import java.time.Instant;
import java.time.ZoneId;
import java.util.TimeZone;

class ClockPresentation implements StatusBarWidget.MultipleTextValuesPresentation {

    private final SettingsService settingsService = ApplicationManager.getApplication().getService(SettingsService.class);

    public ClockPresentation(String widgetId) {
        this.widgetId = widgetId;
    }

    private final String widgetId;

    @Override
    public String getTooltipText() {
        if (widgetId.equals(Globals.WIDGET_ID)) {
            return getTooltipFromConfiguredTz(settingsService.getClock1TZ());
        }
        return getTooltipFromConfiguredTz(settingsService.getClock2TZ());
    }

    private String getTooltipFromConfiguredTz(String tz) {
        if (tz.startsWith("GMT") || tz.equals("UTC") || tz.equals("CST") || tz.equals("EST") || tz.equals("PST")) {
            return tz;
        }
        ZoneId zoneId = ZoneId.of(tz);
        return zoneId.getId() + " (GMT " + zoneId.getRules().getStandardOffset(Instant.now()).toString() + ")";
    }

    @Override
    public @Nullable Consumer<MouseEvent> getClickConsumer() {
        return null;
    }

    @Override
    public @Nullable("null means the widget is unable to show the popup") ListPopup getPopupStep() {
        return null;
    }

    @Override
    public @Nullable String getSelectedValue() {
        if (widgetId.equals(Globals.WIDGET_ID)) {
            if (!settingsService.getEnableClock1()) {
                return null;
            }
            return getDateFromConfiguredTz(settingsService.getClock1TZ());
        } else {
            if (!settingsService.getEnableClock2()) {
                return null;
            }
            return getDateFromConfiguredTz(settingsService.getClock2TZ());
        }
    }

    private String getDateFromConfiguredTz(String tz) {
        if (tz.startsWith("GMT") || tz.equals("UTC") || tz.equals("CST") || tz.equals("EST") || tz.equals("PST")) {
            ZoneId zoneId = TimeZone.getTimeZone(tz).toZoneId();
            return tz + ": " + DateUtils.getDate(zoneId);
        }
        return " " + DateUtils.getDate(ZoneId.of(tz));
    }

    @Override
    public @Nullable Icon getIcon() {
        String zoneId;
        if (widgetId.equals(Globals.WIDGET_ID)) {
            if (!settingsService.getEnableClock1()) {
                return null;
            }
            zoneId = settingsService.getClock1TZ();
        } else {
            zoneId = settingsService.getClock2TZ();
            if (!settingsService.getEnableClock2()) {
                return null;
            }
        }
        String flag = DateUtils.findFlagByTz(zoneId);
        if (flag == null) {
            return null;
        }
        return IconLoader.getIcon("/worldclock/flags/" + flag + ".svg", ClockPresentation.class);
    }
}
