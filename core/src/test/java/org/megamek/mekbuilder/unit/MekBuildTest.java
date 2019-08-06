package org.megamek.mekbuilder.unit;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 *
 */
class MekBuildTest {

    @Test
    void testBiped() {
        MekBuild mek = new MekBuild();
        mek.setConfiguration(MekConfiguration.getConfigurations(UnitType.BATTLE_MEK, MekConfiguration.SubType.STANDARD_BIPED));

        assertAll(
                () -> assertTrue(mek.isBiped()),
                () -> assertFalse(mek.isQuad()),
                () -> assertFalse(mek.isTripod()),
                () -> assertEquals(mek.slotsInLocation(UnitLocation.MEK_HEAD), 6),
                () -> assertEquals(mek.slotsInLocation(UnitLocation.MEK_CTORSO), 12),
                () -> assertEquals(mek.slotsInLocation(UnitLocation.MEK_RTORSO), 12),
                () -> assertEquals(mek.slotsInLocation(UnitLocation.MEK_LTORSO), 12),
                () -> assertEquals(mek.slotsInLocation(UnitLocation.MEK_RARM), 12),
                () -> assertEquals(mek.slotsInLocation(UnitLocation.MEK_LARM), 12),
                () -> assertEquals(mek.slotsInLocation(UnitLocation.MEK_RLEG), 6),
                () -> assertEquals(mek.slotsInLocation(UnitLocation.MEK_LLEG), 6),
                () -> assertEquals(mek.slotsInLocation(UnitLocation.MEK_CLEG), 0),
                () -> assertEquals(mek.slotsInLocation(UnitLocation.MEK_RFLEG), 0),
                () -> assertEquals(mek.slotsInLocation(UnitLocation.MEK_LFLEG), 0)
        );
    }

    @Test
    void testQuad() {
        MekBuild mek = new MekBuild();
        mek.setConfiguration(MekConfiguration.getConfigurations(UnitType.BATTLE_MEK, MekConfiguration.SubType.STANDARD_QUAD));

        assertAll(
                () -> assertFalse(mek.isBiped()),
                () -> assertTrue(mek.isQuad()),
                () -> assertFalse(mek.isTripod()),
                () -> assertEquals(mek.slotsInLocation(UnitLocation.MEK_HEAD), 6),
                () -> assertEquals(mek.slotsInLocation(UnitLocation.MEK_CTORSO), 12),
                () -> assertEquals(mek.slotsInLocation(UnitLocation.MEK_RTORSO), 12),
                () -> assertEquals(mek.slotsInLocation(UnitLocation.MEK_LTORSO), 12),
                () -> assertEquals(mek.slotsInLocation(UnitLocation.MEK_RARM), 0),
                () -> assertEquals(mek.slotsInLocation(UnitLocation.MEK_LARM), 0),
                () -> assertEquals(mek.slotsInLocation(UnitLocation.MEK_RLEG), 6),
                () -> assertEquals(mek.slotsInLocation(UnitLocation.MEK_LLEG), 6),
                () -> assertEquals(mek.slotsInLocation(UnitLocation.MEK_CLEG), 0),
                () -> assertEquals(mek.slotsInLocation(UnitLocation.MEK_RFLEG), 6),
                () -> assertEquals(mek.slotsInLocation(UnitLocation.MEK_LFLEG), 6)
        );
    }

    @Test
    void testTripod() {
        MekBuild mek = new MekBuild();
        mek.setConfiguration(MekConfiguration.getConfigurations(UnitType.BATTLE_MEK, MekConfiguration.SubType.STANDARD_TRIPOD));

        assertAll(
                () -> assertFalse(mek.isBiped()),
                () -> assertFalse(mek.isQuad()),
                () -> assertTrue(mek.isTripod()),
                () -> assertEquals(mek.slotsInLocation(UnitLocation.MEK_HEAD), 6),
                () -> assertEquals(mek.slotsInLocation(UnitLocation.MEK_CTORSO), 12),
                () -> assertEquals(mek.slotsInLocation(UnitLocation.MEK_RTORSO), 12),
                () -> assertEquals(mek.slotsInLocation(UnitLocation.MEK_LTORSO), 12),
                () -> assertEquals(mek.slotsInLocation(UnitLocation.MEK_RARM), 12),
                () -> assertEquals(mek.slotsInLocation(UnitLocation.MEK_LARM), 12),
                () -> assertEquals(mek.slotsInLocation(UnitLocation.MEK_RLEG), 6),
                () -> assertEquals(mek.slotsInLocation(UnitLocation.MEK_LLEG), 6),
                () -> assertEquals(mek.slotsInLocation(UnitLocation.MEK_CLEG), 6),
                () -> assertEquals(mek.slotsInLocation(UnitLocation.MEK_RFLEG), 0),
                () -> assertEquals(mek.slotsInLocation(UnitLocation.MEK_LFLEG), 0)
        );
    }

    @Test
    void testBipedStructureTonnage() {
        MekBuild biped = new MekBuild();
        biped.setTonnage(50);

        assertEquals(biped.getStructureTonnage(), 5);
    }

    @Test
    void testQuadStructureTonnage() {
        MekBuild quad = new MekBuild();
        quad.setConfiguration(MekConfiguration.getConfigurations(UnitType.BATTLE_MEK, MekConfiguration.SubType.STANDARD_QUAD));
        quad.setTonnage(50);

        assertEquals(quad.getStructureTonnage(), 5);
    }

    @Test
    void testTripodStructureTonnage() {
        MekBuild tripod = new MekBuild();
        tripod.setConfiguration(MekConfiguration.getConfigurations(UnitType.BATTLE_MEK, MekConfiguration.SubType.STANDARD_TRIPOD));
        tripod.setTonnage(50);

        assertEquals(tripod.getStructureTonnage(), 5.5);
    }

    @Test
    void testBaseMP() {
        MekBuild locust = new MekBuild();
        locust.setTonnage(20);
        locust.getEngine().setRating(20 * 8);
        MekBuild awesome = new MekBuild();
        awesome.setTonnage(85);
        awesome.getEngine().setRating(85 * 3);

        assertAll(
                () -> assertEquals(locust.getBaseWalkMP(), 8),
                () -> assertEquals(locust.getBaseRunMP(), 12),
                () -> assertEquals(awesome.getBaseWalkMP(), 3),
                () -> assertEquals(awesome.getBaseRunMP(), 5)
        );
    }

}