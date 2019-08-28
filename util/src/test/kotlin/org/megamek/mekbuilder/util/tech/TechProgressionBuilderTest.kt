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
package org.megamek.mekbuilder.util.tech

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.function.Executable
import org.megamek.mekbuilder.tech.Rating
import org.megamek.mekbuilder.tech.TechBase
import org.megamek.mekbuilder.tech.TechLevel
import org.megamek.mekbuilder.tech.TechStage

internal class TechProgressionBuilderTest {

    @Test
    fun testTechProgressionDSL() {
        val tp = techProgression {
            techBase = TechBase.IS
            rating = Rating.RATING_D
            availability = "DXFF"
            isProgression {
                prototype = "2439(TH)"
                production = "2443(TH)"
                common = "2470"
                extinction = "2520"
            }
            staticLevel = TechLevel.ADVANCED
        }

        assertAll(
                Executable{ assertEquals(tp.techBase(), TechBase.IS)},
                Executable{ assertEquals(tp.techRating(), Rating.RATING_D)},
                Executable{ assertEquals(tp.baseAvailability(0), Rating.RATING_D)},
                Executable{ assertEquals(tp.baseAvailability(1), Rating.RATING_X)},
                Executable{ assertEquals(tp.baseAvailability(2), Rating.RATING_F)},
                Executable{ assertEquals(tp.baseAvailability(3), Rating.RATING_F)},
                Executable{ assertEquals(tp.getDate(TechStage.PROTOTYPE), 2439)},
                Executable{ assertEquals(tp.getDate(TechStage.PRODUCTION), 2443)},
                Executable{ assertEquals(tp.getDate(TechStage.COMMON), 2470)},
                Executable{ assertEquals(tp.getDate(TechStage.EXTINCTION), 2520)},
                Executable{ assertEquals(tp.staticTechLevel(), TechLevel.ADVANCED)}
        )
    }
}