package lermitage.intellij.worldclock.statusbar;

import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.StatusBar;
import com.intellij.openapi.wm.StatusBarWidget;
import lermitage.intellij.worldclock.Globals;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.time.ZoneId;

@SuppressWarnings("WeakerAccess")
public class ClockStatusWidget implements StatusBarWidget {

    private final Logger LOG = Logger.getInstance(getClass().getName());
    private final Project project;
    private final String widgetId;
    private final String icon;
    private final ZoneId zoneId;
    private boolean forceExit = false;
    private Thread updateThread = null;

    @Contract(pure = true)
    public ClockStatusWidget(Project project, String widgetId, String icon, ZoneId zoneId) {
        this.project = project;
        this.widgetId = widgetId;
        this.icon = icon;
        this.zoneId = zoneId;
    }

    @NotNull
    @Override
    public String ID() {
        return Globals.PLUGIN_ID;
    }

    @Nullable
    @Override
    public WidgetPresentation getPresentation() {
        return new ClockPresentation(project, this, widgetId, icon, zoneId);
    }

    @Override
    public void install(@NotNull StatusBar statusBar) {
        ApplicationManager.getApplication().executeOnPooledThread(() -> updateWidget(statusBar));
    }

    @SuppressWarnings("BusyWait")
    private void updateWidget(StatusBar statusBar) {
        try {
            updateThread = Thread.currentThread();
            LOG.info("Registered updateThread " + updateThread.getId());
            while (!forceExit) {
                statusBar.updateWidget(widgetId);
                Thread.sleep(30_000);
            }
        } catch (InterruptedException e) {
            LOG.info("App disposed, forced updateThread interruption.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void dispose() {
        forceExit = true;
        if (updateThread != null && !updateThread.isInterrupted()) {
            LOG.info("Interrupting updateThread " + updateThread.getId());
            updateThread.interrupt();
        }
    }
}
