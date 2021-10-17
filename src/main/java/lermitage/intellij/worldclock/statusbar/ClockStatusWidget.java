package lermitage.intellij.worldclock.statusbar;

import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.StatusBar;
import com.intellij.openapi.wm.StatusBarWidget;
import com.intellij.openapi.wm.WindowManager;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Timer;
import java.util.TimerTask;

@SuppressWarnings("WeakerAccess")
public class ClockStatusWidget implements StatusBarWidget {

    private final String widgetId;
    private final StatusBar statusBar;
    private Timer timer;

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
        try {
            timer = new Timer();
            timer.scheduleAtFixedRate(new TimerTask() {
                @Override
                public void run() {
                    statusBar.updateWidget(widgetId);
                }
            }, 0, 30_000);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void dispose() {
        if (timer != null) {
            timer.cancel();
            timer.purge();
        }
    }
}
