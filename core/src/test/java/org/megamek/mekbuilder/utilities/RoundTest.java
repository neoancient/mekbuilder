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
package org.megamek.mekbuilder.utilities;

import org.junit.jupiter.api.Test;
import org.megamek.mekbuilder.unit.UnitBuild;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;

/**
 *
 */
class RoundTest {

    private static final double DELTA = 0.0001;

    @Test
    void roundToTon() {
        assertAll(
                () -> assertEquals(Round.roundToTon(5.1), 6, DELTA),
                () -> assertEquals(Round.roundToTon(5.0), 5.0, DELTA)
        );
    }

    @Test
    void roundToHalfTon() {
        assertAll(
                () -> assertEquals(Round.roundToHalfTon(5.0), 5.0, DELTA),
                () -> assertEquals(Round.roundToHalfTon(5.1), 5.5, DELTA),
                () -> assertEquals(Round.roundToHalfTon(5.5), 5.5, DELTA),
                () -> assertEquals(Round.roundToHalfTon(5.6), 6.0, DELTA)
        );
    }

    @Test
    void roundToKilo() {
        assertAll(
                () -> assertEquals(Round.roundToKilo(5.0), 5.0, DELTA),
                () -> assertEquals(Round.roundToKilo(5.001), 5.001, DELTA),
                () -> assertEquals(Round.roundToKilo(5.0001), 5.0, DELTA),
                () -> assertEquals(Round.roundToKilo(5.0005), 5.001, DELTA)
        );
    }

    @Test
    void round() {
        UnitBuild tonStandard = Mockito.mock(UnitBuild.class);
        Mockito.when(tonStandard.usesKilogramStandard()).thenReturn(false);
        UnitBuild kiloStandard = Mockito.mock(UnitBuild.class);
        Mockito.when(kiloStandard.usesKilogramStandard()).thenReturn(true);

        assertAll(
                () -> assertEquals(Round.round(5.0, tonStandard), 5.0, DELTA),
                () -> assertEquals(Round.round(5.0, kiloStandard), 5.0, DELTA),
                () -> assertEquals(Round.round(5.1, tonStandard), 5.5, DELTA),
                () -> assertEquals(Round.round(5.1, kiloStandard), 5.1, DELTA)
        );
    }
}