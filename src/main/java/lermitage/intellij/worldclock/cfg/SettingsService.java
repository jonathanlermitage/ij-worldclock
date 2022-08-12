// SPDX-License-Identifier: MIT

package lermitage.intellij.worldclock.cfg;

import com.intellij.openapi.components.PersistentStateComponent;
import com.intellij.openapi.components.State;
import com.intellij.openapi.components.Storage;
import com.intellij.util.xmlb.XmlSerializerUtil;
import org.jetbrains.annotations.NotNull;

// see http://www.jetbrains.org/intellij/sdk/docs/basics/persisting_state_of_components.html
@SuppressWarnings({"WeakerAccess", "unused"})
@State(
        name = "WorldClockSettings",
        storages = @Storage("lermitage-worldclock.xml")
)
public class SettingsService implements PersistentStateComponent<SettingsService> {

    public String clock1TZ;
    public String clock2TZ;
    public Boolean enableClock1;
    public Boolean enableClock2;
    public Boolean use24HDateFormat;

    @Override
    public SettingsService getState() {
        return this;
    }

    @Override
    public void loadState(@NotNull SettingsService state) {
        XmlSerializerUtil.copyBean(state, this);
    }

    // config keys getters/setters

    public String getClock1TZ() {
        return clock1TZ == null ? Defaults.CLOCK1_TZ : clock1TZ;
    }

    public void setClock1TZ(String clock1TZ) {
        this.clock1TZ = clock1TZ;
    }

    public String getClock2TZ() {
        return clock2TZ == null ? Defaults.CLOCK2_TZ : clock2TZ;
    }

    public void setClock2TZ(String clock2TZ) {
        this.clock2TZ = clock2TZ;
    }

    public Boolean getEnableClock1() {
        return enableClock1 == null ? Defaults.ENABLE_CLOCK_1 : enableClock1;
    }

    public void setEnableClock1(Boolean enableClock1) {
        this.enableClock1 = enableClock1;
    }

    public Boolean getEnableClock2() {
        return enableClock2 == null ? Defaults.ENABLE_CLOCK_2 : enableClock2;
    }

    public void setEnableClock2(Boolean enableClock2) {
        this.enableClock2 = enableClock2;
    }

    public Boolean getUse24HDateFormat() {
        return use24HDateFormat != null && use24HDateFormat;
    }

    public void setUse24HDateFormat(Boolean use24HDateFormat) {
        this.use24HDateFormat = use24HDateFormat;
    }
}
