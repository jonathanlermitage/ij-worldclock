package lermitage.intellij.worldclock.statusbar;

import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.wm.StatusBar;
import com.intellij.openapi.wm.StatusBarWidget;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.time.ZoneId;
import java.util.Timer;
import java.util.TimerTask;

@SuppressWarnings("WeakerAccess")
public class ClockStatusWidget implements StatusBarWidget {

    private final String widgetId;
    private final String icon;
    private final ZoneId zoneId;
    private Timer timer;

    @Contract(pure = true)
    public ClockStatusWidget(String widgetId, String icon, ZoneId zoneId) {
        this.widgetId = widgetId;
        this.icon = icon;
        this.zoneId = zoneId;
    }

    @NotNull
    @Override
    public String ID() {
        return widgetId;
    }

    @Nullable
    @Override
    public WidgetPresentation getPresentation() {
        return new ClockPresentation(this, widgetId, icon, zoneId);
    }

    @Override
    public void install(@NotNull StatusBar statusBar) {
        ApplicationManager.getApplication().executeOnPooledThread(() -> updateWidget(statusBar));
    }

    private void updateWidget(StatusBar statusBar) {
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
