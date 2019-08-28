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
package org.megamek.mekbuilder.tech;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TechProgressionTest {
    @Test
    void testProgression() {
        final TechProgression prog = new TechProgression.Parser("ALL|A|AAAA|2400,2500,3050,2810,3040|2400,2500,2820|STANDARD")
                .parse();

        assertAll(
                () -> assertEquals(prog.introDate().intValue(), 2400),
                () -> assertEquals(prog.getDate(TechStage.PROTOTYPE).intValue(), 2400),
                () -> assertEquals(prog.getDate(TechStage.PRODUCTION).intValue(), 2500),
                () -> assertEquals(prog.getDate(TechStage.COMMON).intValue(), 2820),
                () -> assertNull(prog.getDate(TechStage.EXTINCTION)),
                () -> assertEquals(prog.getDate(TechStage.REINTRODUCTION).intValue(), 3040),
                () -> assertEquals(prog.introDate(false).intValue(), 2400),
                () -> assertEquals(prog.getDate(TechStage.PROTOTYPE, false).intValue(), 2400),
                () -> assertEquals(prog.getDate(TechStage.PRODUCTION, false).intValue(), 2500),
                () -> assertEquals(prog.getDate(TechStage.COMMON, false).intValue(), 3050),
                () -> assertEquals(prog.getDate(TechStage.EXTINCTION, false).intValue(), 2810),
                () -> assertEquals(prog.getDate(TechStage.REINTRODUCTION, false).intValue(), 3040),
                () -> assertEquals(prog.introDate(true).intValue(), 2400),
                () -> assertEquals(prog.getDate(TechStage.PROTOTYPE, true).intValue(), 2400),
                () -> assertEquals(prog.getDate(TechStage.PRODUCTION, true).intValue(), 2500),
                () -> assertEquals(prog.getDate(TechStage.COMMON, true).intValue(), 2820),
                () -> assertNull(prog.getDate(TechStage.EXTINCTION, true)),
                () -> assertNull(prog.getDate(TechStage.REINTRODUCTION, true)),
                () -> assertFalse(prog.extinct(2900)),
                () -> assertTrue(prog.extinct(2900, false)),
                () -> assertFalse(prog.extinct(2900, true))
        );
    }
}