package org.megamek.mekbuilder.component;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.megamek.mekbuilder.unit.MekBuild;
import org.megamek.mekbuilder.unit.UnitLocation;

import static org.junit.jupiter.api.Assertions.*;

/**
 *
 */
class MekEngineMountTest {

    private MekEngineMount engine;

    @BeforeEach
    void init() {
        MekBuild mek = new MekBuild();
        engine = mek.getEngine();
    }

    @Test
    void testMinimumRating() {
        engine.setRating(5);

        assertEquals(engine.getEngineRating(), 10);
    }

    @Test
    void testRoundRatingToFive() {
        engine.setRating(23);

        assertEquals(engine.getEngineRating(), 20);
    }

    @Test
    void testSwitchToLargeEngine() {
        engine.setRating(450);

        assertTrue(engine.getEngine().isLargeEngine());
    }

    @Test
    void testSwitchToStandardEngine() {
        engine.setRating(450);
        engine.setRating(250);

        assertFalse(engine.getEngine().isLargeEngine());
    }

    @Test
    void testStdEngineLocation() {
        assertAll(
                () -> assertTrue(engine.isEngineLocation(UnitLocation.MEK_CTORSO)),
                () -> assertFalse(engine.isEngineLocation(UnitLocation.MEK_RTORSO)),
                () -> assertFalse(engine.isEngineLocation(UnitLocation.MEK_LTORSO)),
                () -> assertFalse(engine.isEngineLocation(UnitLocation.MEK_HEAD)),
                () -> assertFalse(engine.isEngineLocation(UnitLocation.MEK_RARM)),
                () -> assertFalse(engine.isEngineLocation(UnitLocation.MEK_LARM)),
                () -> assertFalse(engine.isEngineLocation(UnitLocation.MEK_RLEG)),
                () -> assertFalse(engine.isEngineLocation(UnitLocation.MEK_LLEG))
        );
    }

    @Test
    void testXLEngineLocation() {
        engine.setComponent(ComponentLibrary.getInstance().getComponent("ISXLEngine"));

        assertAll(
                () -> assertTrue(engine.isEngineLocation(UnitLocation.MEK_CTORSO)),
                () -> assertTrue(engine.isEngineLocation(UnitLocation.MEK_RTORSO)),
                () -> assertTrue(engine.isEngineLocation(UnitLocation.MEK_LTORSO)),
                () -> assertFalse(engine.isEngineLocation(UnitLocation.MEK_HEAD)),
                () -> assertFalse(engine.isEngineLocation(UnitLocation.MEK_RARM)),
                () -> assertFalse(engine.isEngineLocation(UnitLocation.MEK_LARM)),
                () -> assertFalse(engine.isEngineLocation(UnitLocation.MEK_RLEG)),
                () -> assertFalse(engine.isEngineLocation(UnitLocation.MEK_LLEG))
        );
    }

    @Test
    void testBadAssignment() {
        assertThrows(IllegalArgumentException.class,
                () -> engine.setComponent(ComponentLibrary.getInstance().getComponent(ComponentKeys.HEAT_SINK_SINGLE)));
    }
}