// SPDX-License-Identifier: MIT

package lermitage.intellij.worldclock;

import com.intellij.ide.projectView.ProjectView;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.project.ProjectManager;
import com.intellij.openapi.wm.StatusBar;
import com.intellij.openapi.wm.WindowManager;
import lermitage.intellij.worldclock.statusbar.ClockStatusWidget;
import org.jetbrains.annotations.Nullable;

public class IJUtils {

    /**
     * Refresh project status bar.
     */
    private static void refresh(Project project) {
        if (IJUtils.isAlive(project)) {
            ProjectView view = ProjectView.getInstance(project);
            if (view != null) {
                StatusBar statusBar = WindowManager.getInstance().getStatusBar(project);
                if (statusBar != null) {
                    statusBar.updateWidget(Globals.WIDGET_ID);
                    statusBar.updateWidget(Globals.WIDGET_ID_2);
                    for (String widgetId : new String[]{Globals.WIDGET_ID, Globals.WIDGET_ID_2}) {
                        ClockStatusWidget widget = (ClockStatusWidget) statusBar.getWidget(widgetId);
                        if (widget != null) {
                            widget.reload();
                        }
                    }
                }
            }
        }
    }

    /**
     * Refresh all opened project status bar.
     */
    public static void refreshOpenedProjects() {
        Project[] projects = ProjectManager.getInstance().getOpenProjects();
        for (Project project : projects) {
            refresh(project);
        }
    }

    /**
     * Return true if the project can be manipulated. Project is not null, not disposed, etc.
     * Developed to fix <a href="https://github.com/jonathanlermitage/intellij-extra-icons-plugin/issues/39">issue #39</a>.
     */
    private static boolean isAlive(@Nullable Project project) {
        return project != null && !project.isDisposed();
    }
}
