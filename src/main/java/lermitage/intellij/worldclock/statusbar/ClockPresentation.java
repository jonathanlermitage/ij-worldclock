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

class ClockPresentation implements StatusBarWidget.MultipleTextValuesPresentation {

    private final SettingsService settingsService = ApplicationManager.getApplication().getService(SettingsService.class);

    public ClockPresentation(String widgetId) {
        this.widgetId = widgetId;
    }

    private final String widgetId;

    @Override
    public String getTooltipText() {
        ZoneId zoneId;
        if (widgetId.equals(Globals.WIDGET_ID)) {
            zoneId = ZoneId.of(settingsService.getClock1TZ());
        } else {
            zoneId = ZoneId.of(settingsService.getClock2TZ());
        }
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
            return " " + DateUtils.getDate(ZoneId.of(settingsService.getClock1TZ()));
        } else {
            if (!settingsService.getEnableClock2()) {
                return null;
            }
            return " " + DateUtils.getDate(ZoneId.of(settingsService.getClock2TZ()));
        }
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
        return IconLoader.getIcon("/worldclock/flags/" + DateUtils.findFlagByTz(zoneId) + ".svg", ClockPresentation.class);
    }
}
