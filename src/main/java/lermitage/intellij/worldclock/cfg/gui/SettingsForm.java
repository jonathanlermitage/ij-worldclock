package lermitage.intellij.worldclock.cfg.gui;

import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.options.Configurable;
import lermitage.intellij.worldclock.DateUtils;
import lermitage.intellij.worldclock.IJUtils;
import lermitage.intellij.worldclock.cfg.SettingsService;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.Nullable;

import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.event.ActionListener;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class SettingsForm implements Configurable {
    private JPanel mainPane;
    private JLabel clock1Label;
    private JComboBox<String> clock1Place;
    private JCheckBox clock1Enable;
    private JLabel clock2Label;
    private JComboBox<String> clock2Place;
    private JCheckBox clock2Enable;

    private boolean modified = false;
    private final SettingsService settingsService = ApplicationManager.getApplication().getService(SettingsService.class);

    @Nls(capitalization = Nls.Capitalization.Title)
    @Override
    public String getDisplayName() {
        return "World Clock";
    }

    @Override
    public @Nullable JComponent createComponent() {
        ActionListener actionListener = e -> modified = true;

        String configuredClock1TZ = settingsService.getClock1TZ();
        String configuredClock2TZ = settingsService.getClock2TZ();

        List<String> tzs = DateUtils.getAllAvailableTZAndFlags().keySet().stream()
                .sorted(Comparator.comparing(String::toUpperCase))
                .collect(Collectors.toList());
        for (int placeIdx = 0; placeIdx < tzs.size(); placeIdx++) {
            clock1Place.addItem(tzs.get(placeIdx));
            clock2Place.addItem(tzs.get(placeIdx));
            if (tzs.get(placeIdx).equalsIgnoreCase(configuredClock1TZ)) {
                clock1Place.setSelectedIndex(placeIdx);
            }
            if (tzs.get(placeIdx).equalsIgnoreCase(configuredClock2TZ)) {
                clock2Place.setSelectedIndex(placeIdx);
            }
        }

        clock1Label.setText("Clock #1 (left):");
        clock1Place.addActionListener(actionListener);
        clock1Enable.setText("show in status bar");
        clock1Enable.setSelected(settingsService.getEnableClock1());
        clock1Enable.addActionListener(actionListener);

        clock2Label.setText("Clock #2 (right):");
        clock2Place.addActionListener(actionListener);
        clock2Enable.setText("show in status bar");
        clock2Enable.setSelected(settingsService.getEnableClock2());
        clock2Enable.addActionListener(actionListener);

        return mainPane;
    }

    @Override
    public boolean isModified() {
        return modified;
    }

    @Override
    public void apply() {
        settingsService.setClock1TZ((String) clock1Place.getSelectedItem());
        settingsService.setClock2TZ((String) clock2Place.getSelectedItem());
        settingsService.setEnableClock1(clock1Enable.isSelected());
        settingsService.setEnableClock2(clock2Enable.isSelected());
        modified = false;
        IJUtils.refreshOpenedProjects();
    }

    @Override
    public void reset() {
        String configuredClock1TZ = settingsService.getClock1TZ();
        String configuredClock2TZ = settingsService.getClock2TZ();

        List<String> tzs = DateUtils.getAllAvailableTZAndFlags().keySet().stream()
                .sorted(Comparator.comparing(String::toUpperCase))
                .collect(Collectors.toList());
        for (int placeIdx = 0; placeIdx < tzs.size(); placeIdx++) {
            if (tzs.get(placeIdx).equalsIgnoreCase(configuredClock1TZ)) {
                clock1Place.setSelectedIndex(placeIdx);
            }
            if (tzs.get(placeIdx).equalsIgnoreCase(configuredClock2TZ)) {
                clock2Place.setSelectedIndex(placeIdx);
            }
        }

        clock1Enable.setSelected(settingsService.getEnableClock1());
        clock2Enable.setSelected(settingsService.getEnableClock2());
        modified = false;
    }
}
