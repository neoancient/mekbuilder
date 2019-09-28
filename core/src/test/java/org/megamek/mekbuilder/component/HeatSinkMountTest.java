package org.megamek.mekbuilder.component;

import org.junit.jupiter.api.Test;
import org.megamek.mekbuilder.unit.MekBuild;

import static org.junit.jupiter.api.Assertions.*;

/**
 *
 */
class HeatSinkMountTest {

    @Test
    void noExtraSinksButSlotsRequired() {
        MekBuild mek = new MekBuild();
        mek.getHeatSinkMount().setCount(0);
        mek.setEngineRating(120);

        assertAll(
                () -> assertEquals(0.0, mek.getHeatSinkMount().getComponentWeight(), 0.001),
                () -> assertEquals(6, mek.getHeatSinkMount().getComponentSlots()),
                () -> assertEquals(10, mek.getHeatSinkMount().heatDissipation())
        );
    }

    @Test
    void noExtraSinksNoSlotsRequired() {
        MekBuild mek = new MekBuild();
        mek.getHeatSinkMount().setCount(0);
        mek.setEngineRating(260);

        assertAll(
                () -> assertEquals(0.0, mek.getHeatSinkMount().getComponentWeight(), 0.001),
                () -> assertEquals(0, mek.getHeatSinkMount().getComponentSlots()),
                () -> assertEquals(10, mek.getHeatSinkMount().heatDissipation())
        );
    }

    @Test
    void extraSinksAddsWeightAndSlots() {
        MekBuild mek = new MekBuild();
        mek.getHeatSinkMount().setCount(2);
        mek.setEngineRating(120);

        assertAll(
                () -> assertEquals(2.0, mek.getHeatSinkMount().getComponentWeight(), 0.001),
                () -> assertEquals(8, mek.getHeatSinkMount().getComponentSlots()),
                () -> assertEquals(12, mek.getHeatSinkMount().heatDissipation())
        );
    }

    @Test
    void doubleSinksIncreaseSlotRequirement() {
        MekBuild mek = new MekBuild();
        mek.getHeatSinkMount().setCount(2);
        mek.getHeatSinkMount().setComponent(ComponentLibrary.getInstance().getComponent(ComponentKeys.HEAT_SINK_DOUBLE_IS));
        mek.setEngineRating(120);

        assertAll(
                () -> assertEquals(2.0, mek.getHeatSinkMount().getComponentWeight(), 0.001),
                () -> assertEquals(24, mek.getHeatSinkMount().getComponentSlots()),
                () -> assertEquals(24, mek.getHeatSinkMount().heatDissipation())
        );
    }

}