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
import org.megamek.mekbuilder.component.*;
import org.megamek.mekbuilder.tech.ConstructionOptionKey;
import org.megamek.mekbuilder.tech.UnitConstructionOption;

import static org.junit.jupiter.api.Assertions.*;

/**
 *
 */
class MekBuildTest {

    @Test
    void testBiped() {
        MekBuild mek = new MekBuild();
        mek.setConfiguration(MekConfiguration.getConfiguration(UnitType.BATTLE_MEK, MekConfiguration.SubType.STANDARD_BIPED));

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
        mek.setConfiguration(MekConfiguration.getConfiguration(UnitType.BATTLE_MEK, MekConfiguration.SubType.STANDARD_QUAD));

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
        mek.setConfiguration(MekConfiguration.getConfiguration(UnitType.BATTLE_MEK, MekConfiguration.SubType.STANDARD_TRIPOD));

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
        quad.setConfiguration(MekConfiguration.getConfiguration(UnitType.BATTLE_MEK, MekConfiguration.SubType.STANDARD_QUAD));
        quad.setTonnage(50);

        assertEquals(quad.getStructureTonnage(), 5);
    }

    @Test
    void testTripodStructureTonnage() {
        MekBuild tripod = new MekBuild();
        tripod.setConfiguration(MekConfiguration.getConfiguration(UnitType.BATTLE_MEK, MekConfiguration.SubType.STANDARD_TRIPOD));
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

    @Test
    void tonnageChangeAffectsEngineRating() {
        MekBuild mek = new MekBuild();
        mek.setTonnage(20);
        mek.setEngineRating(20 * 8);

        mek.setTonnage(30);

        assertEquals(30 * 8, mek.getEngineRating());
    }

    @Test
    void baseWalkChangeAffectsEngineRating() {
        MekBuild mek = new MekBuild();
        mek.setTonnage(20);
        mek.setEngineRating(20 * 6);

        mek.setBaseWalkMP(8);

        assertEquals(20 * 8, mek.getEngineRating());
    }

    @Test
    void engineSwitchToLarge() {
        MekBuild mek = new MekBuild();
        mek.setTonnage(50);

        mek.setBaseWalkMP(9);

        assertTrue(mek.getEngineType().isLargeEngine());
    }

    @Test
    void engineSwitchFromLarge() {
        MekBuild mek = new MekBuild();
        mek.setTonnage(50);
        mek.setBaseWalkMP(9);

        mek.setBaseWalkMP(6);

        assertFalse(mek.getEngineType().isLargeEngine());
    }

    @Test
    void walkMPLimitedByMaxEngineRating() {
        MekBuild hasLarge = new MekBuild();
        hasLarge.setTonnage(50);
        MekBuild noLarge = new MekBuild();
        noLarge.setEngineType((MVFEngine) ComponentLibrary.getInstance().getComponent(ComponentKeys.ENGINE_COMPACT));
        noLarge.setTonnage(50);

        hasLarge.setBaseWalkMP(12);
        noLarge.setBaseWalkMP(12);

        assertAll(
                () -> assertEquals(500 / 50, hasLarge.getBaseWalkMP()),
                () -> assertEquals(400 / 50, noLarge.getBaseWalkMP()));
    }

    @Test
    void primitiveHasEngineRating20PercentHigher() {
        MekBuild mek = new MekBuild();
        mek.setTonnage(20);
        mek.setConfiguration(MekConfiguration.getConfiguration(mek.getUnitType(),
                MekConfiguration.SubType.PRIMITIVE_BIPED));
        mek.setBaseConstructionOption((UnitConstructionOption) ConstructionOptionKey.MEK_PRIMITIVE.get());

        mek.setBaseWalkMP(5);

        assertEquals(120, mek.getEngineRating());
    }

    @Test
    void mechJumpJetsHeatCalc() {
        MekBuild mek = new MekBuild();
        SecondaryMotiveSystem jj = (SecondaryMotiveSystem) ComponentLibrary.getInstance().getComponent(ComponentKeys.MEK_JJ);
        mek.setSecondaryMotiveType(jj);

        mek.setSecondaryMP(4);

        assertEquals(4, mek.secondaryMotiveHeat());
    }

    @Test
    void mechJumpMinimumHeat() {
        MekBuild mek = new MekBuild();
        SecondaryMotiveSystem jj = (SecondaryMotiveSystem) ComponentLibrary.getInstance().getComponent(ComponentKeys.MEK_JJ);
        mek.setSecondaryMotiveType(jj);

        mek.setSecondaryMP(2);

        assertEquals(3, mek.secondaryMotiveHeat());
    }

    @Test
    void mechIJJCalcHeat() {
        SecondaryMotiveSystem ijj = (SecondaryMotiveSystem) ComponentLibrary.getInstance().getComponent(ComponentKeys.MEK_IJJ);
        MekBuild mp4 = new MekBuild();
        MekBuild mp7 = new MekBuild();
        MekBuild mp8 = new MekBuild();

        mp4.setSecondaryMotiveType(ijj);
        mp4.setSecondaryMP(4);
        mp7.setSecondaryMotiveType(ijj);
        mp7.setSecondaryMP(7);
        mp8.setSecondaryMotiveType(ijj);
        mp8.setSecondaryMP(8);

        assertAll(
                () -> assertEquals(3, mp4.secondaryMotiveHeat()),
                () -> assertEquals(4, mp7.secondaryMotiveHeat()),
                () -> assertEquals(4, mp8.secondaryMotiveHeat())
        );
    }

    @Test
    void xxlEngineDoublesJumpHeat() {
        MekBuild mek = new MekBuild();
        SecondaryMotiveSystem jj = (SecondaryMotiveSystem) ComponentLibrary.getInstance().getComponent(ComponentKeys.MEK_JJ);
        mek.setSecondaryMotiveType(jj);
        mek.setEngineType((MVFEngine) ComponentLibrary.getInstance().getComponent(ComponentKeys.ENGINE_XXL_IS));

        mek.setSecondaryMP(4);

        assertEquals(8, mek.secondaryMotiveHeat());
    }

    @Test
    void mechUMUCalcHeat() {
        SecondaryMotiveSystem umu = (SecondaryMotiveSystem) ComponentLibrary.getInstance().getComponent(ComponentKeys.MEK_UMU);
        MekBuild mek = new MekBuild();
        mek.setSecondaryMotiveType(umu);

        mek.setSecondaryMP(4);

        assertEquals(1, mek.secondaryMotiveHeat());
    }

}