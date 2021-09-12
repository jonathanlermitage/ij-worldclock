package lermitage.intellij.worldclock.statusbar;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.StatusBar;
import com.intellij.openapi.wm.StatusBarWidget;
import com.intellij.openapi.wm.StatusBarWidgetFactory;
import lermitage.intellij.worldclock.DateUtils;
import lermitage.intellij.worldclock.Globals;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.NotNull;

public class ClockWidgetFactory implements StatusBarWidgetFactory {

    @NotNull
    @Override
    public String getId() {
        return Globals.PLUGIN_ID;
    }

    @Nls
    @NotNull
    @Override
    public String getDisplayName() {
        return "World Clock 1";
    }

    @Override
    public boolean isAvailable(@NotNull Project project) {
        return true;
    }

    @NotNull
    @Override
    public StatusBarWidget createWidget(@NotNull Project project) {
        return new ClockStatusWidget(project, Globals.PLUGIN_ID, "ca.svg", DateUtils.caZoneId);
    }

    @Override
    public void disposeWidget(@NotNull StatusBarWidget widget) {
    }

    @Override
    public boolean canBeEnabledOn(@NotNull StatusBar statusBar) {
        return true;
    }
}
