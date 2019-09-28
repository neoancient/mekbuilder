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

    @Test
    void xxlEngineRunsHot() {
        MekBuild mek = new MekBuild();
        mek.setEngineType((MVFEngine) ComponentLibrary.getInstance().getComponent(ComponentKeys.ENGINE_XXL_IS));

        assertEquals(6, mek.movementHeat());
    }

    @Test
    void iceEngineNoMoveHeat() {
        MekBuild mek = new MekBuild();
        mek.setEngineType((MVFEngine) ComponentLibrary.getInstance().getComponent(ComponentKeys.ENGINE_ICE));

        assertEquals(0, mek.movementHeat());
    }
}