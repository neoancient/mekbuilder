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

import org.junit.jupiter.api.Test;
import org.megamek.mekbuilder.unit.UnitWeightClass;

import java.util.List;
import java.util.Set;
import java.util.StringJoiner;

import static org.junit.jupiter.api.Assertions.*;

class ComponentSwitchTest {
    @Test
    void testIntParser() {
        assertAll(
                () -> assertEquals(DataParsers.intParser.apply("1", ","), 1),
                () -> assertEquals(DataParsers.intParser.apply("-2", ","), -2)
        );
    }

    @Test
    @SuppressWarnings("unchecked")
    void testDoubleArrayParser() {
        final List<Double> list = (List<Double>) DataParsers.doubleArrayParser.apply("1.1,2.3,-0.4", ",");

        assertAll(
                () -> assertEquals(list.size(), 3),
                () -> assertTrue(list.contains(1.1)),
                () -> assertTrue(list.contains(2.3)),
                () -> assertTrue(list.contains(-0.4))
        );
    }

    @Test
    @SuppressWarnings("unchecked")
    void testWeightClassSetParser() {
        final StringJoiner sj = new StringJoiner(",");
        sj.add(UnitWeightClass.LIGHT.name());
        sj.add(UnitWeightClass.HEAVY.name());
        final Set<UnitWeightClass> set = (Set<UnitWeightClass>) ComponentSwitch.WEIGHT_CLASS.deserializeValue(sj.toString());

        assertAll(
                () -> assertEquals(set.size(), 2),
                () -> assertTrue(set.contains(UnitWeightClass.LIGHT)),
                () -> assertTrue(set.contains(UnitWeightClass.HEAVY))
        );
    }

}