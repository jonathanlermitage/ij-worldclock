package lermitage.intellij.worldclock.statusbar;

import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.StatusBar;
import com.intellij.openapi.wm.StatusBarWidget;
import com.intellij.openapi.wm.WindowManager;
import com.intellij.util.concurrency.AppExecutorUtil;
import lermitage.intellij.worldclock.Globals;
import lermitage.intellij.worldclock.cfg.SettingsService;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

@SuppressWarnings("WeakerAccess")
public class ClockStatusWidget implements StatusBarWidget {

    private static final Logger LOGGER = Logger.getInstance(ClockStatusWidget.class);

    private final SettingsService settingsService = ApplicationManager.getApplication().getService(SettingsService.class);

    private final String widgetId;
    private final StatusBar statusBar;
    private ScheduledFuture<?> timer;

    @Contract(pure = true)
    public ClockStatusWidget(String widgetId, Project project) {
        this.widgetId = widgetId;
        this.statusBar = WindowManager.getInstance().getStatusBar(project);
    }

    @NotNull
    @Override
    public String ID() {
        return widgetId;
    }

    @Nullable
    @Override
    public WidgetPresentation getPresentation() {
        return new ClockPresentation(widgetId);
    }

    @Override
    public void install(@NotNull StatusBar statusBar) {
        ApplicationManager.getApplication().executeOnPooledThread(() -> startIfNeeded(statusBar));
    }

    public void reload() {
        dispose();
        startIfNeeded(statusBar);
    }

    private void startIfNeeded(StatusBar statusBar) {
        if (widgetId.equals(Globals.WIDGET_ID) && settingsService.getEnableClock1() ||
            widgetId.equals(Globals.WIDGET_ID_2) && settingsService.getEnableClock2()) {
            try {
                timer = AppExecutorUtil.getAppScheduledExecutorService().scheduleWithFixedDelay(() -> {
                    statusBar.updateWidget(widgetId);
                }, 0, 30, TimeUnit.SECONDS);
            } catch (Exception e) {
                LOGGER.warn(e);
            }
        }
    }

    @Override
    public void dispose() {
        if (timer != null) {
            timer.cancel(true);
            timer = null;
        }
    }
}
