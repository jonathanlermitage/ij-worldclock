package lermitage.intellij.worldclock.statusbar;

import com.intellij.openapi.ui.popup.ListPopup;
import com.intellij.openapi.util.IconLoader;
import com.intellij.openapi.wm.StatusBarWidget;
import com.intellij.util.Consumer;
import lermitage.intellij.worldclock.DateUtils;
import org.jetbrains.annotations.Nullable;

import javax.swing.Icon;
import java.awt.event.MouseEvent;
import java.time.ZoneId;

class ClockPresentation implements StatusBarWidget.MultipleTextValuesPresentation {

    public ClockPresentation(String icon, ZoneId zoneId) {
        this.icon = icon;
        this.zoneId = zoneId;
    }

    private final String icon;
    private final ZoneId zoneId;

    @Override
    public String getTooltipText() {
        return "";
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
        return " " + DateUtils.getDate(zoneId);
    }

    @Override
    public @Nullable Icon getIcon() {
        return IconLoader.getIcon("/worldclock/" + icon, ClockPresentation.class);
    }
}
