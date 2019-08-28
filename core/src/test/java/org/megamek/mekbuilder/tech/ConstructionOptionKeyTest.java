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

import java.util.StringJoiner;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests to ensure that all built-in construction options are available
 */
class ConstructionOptionKeyTest {
    @Test
    void testAllOptionsLoad() {
        StringJoiner sb = new StringJoiner(",");

        for (ConstructionOptionKey key : ConstructionOptionKey.values()) {
            if (key.get() == null) {
                sb.add(key.name());
            }
        }

        assertEquals("", sb.toString());
    }
}