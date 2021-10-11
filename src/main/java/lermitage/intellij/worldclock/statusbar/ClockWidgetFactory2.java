package lermitage.intellij.worldclock.statusbar;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.StatusBar;
import com.intellij.openapi.wm.StatusBarWidget;
import com.intellij.openapi.wm.StatusBarWidgetFactory;
import lermitage.intellij.worldclock.DateUtils;
import lermitage.intellij.worldclock.Globals;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.NotNull;

public class ClockWidgetFactory2 implements StatusBarWidgetFactory {

    @NotNull
    @Override
    public String getId() {
        return Globals.WIDGET_ID_2;
    }

    @Nls
    @NotNull
    @Override
    public String getDisplayName() {
        return "World Clock 2";
    }

    @Override
    public boolean isAvailable(@NotNull Project project) {
        return true;
    }

    @NotNull
    @Override
    public StatusBarWidget createWidget(@NotNull Project project) {
        return new ClockStatusWidget(Globals.WIDGET_ID_2, "fr.svg", DateUtils.frZoneId);
    }

    @Override
    public void disposeWidget(@NotNull StatusBarWidget widget) {
    }

    @Override
    public boolean canBeEnabledOn(@NotNull StatusBar statusBar) {
        return true;
    }
}