package lermitage.intellij.worldclock.statusbar;

import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.util.IconLoader;
import com.intellij.openapi.wm.StatusBarWidget;
import com.intellij.util.Consumer;
import lermitage.intellij.worldclock.DateUtils;
import lermitage.intellij.worldclock.Globals;
import lermitage.intellij.worldclock.cfg.Defaults;
import lermitage.intellij.worldclock.cfg.SettingsService;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.awt.event.MouseEvent;
import java.time.Instant;
import java.time.ZoneId;

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
        ZoneId zoneId = ZoneId.of(DateUtils.getAliases().getOrDefault(tz, tz));
        return zoneId.getId() + " (GMT " + zoneId.getRules().getStandardOffset(Instant.now()).toString() + ")";
    }

    // removed @Override as MultipleTextValuesPresentation.getClickConsumer is scheduled for removal in a future release
    public @Nullable Consumer<MouseEvent> getClickConsumer() {
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
            return tz + ": " + DateUtils.getDate(tz, settingsService.getUse24HDateFormat());
        }
        return " " + DateUtils.getDate(tz, settingsService.getUse24HDateFormat());
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
        String icon_path = "/worldclock/" + settingsService.getTheme() + "/" + flag + ".svg";
        if (ClockPresentation.class.getResource(icon_path) != null) {
            return IconLoader.getIcon(icon_path, ClockPresentation.class);
        }
        return IconLoader.getIcon("/worldclock/" + Defaults.FLAGS_THEME_FOLDER + "/" + flag + ".svg", ClockPresentation.class);
    }
}
