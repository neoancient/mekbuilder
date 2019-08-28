/*
 * MekBuilder - unit design companion of MegaMek
 * Copyright (C) 2019 The MegaMek Team
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 */
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