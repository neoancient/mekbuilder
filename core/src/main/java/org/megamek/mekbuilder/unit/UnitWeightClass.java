/*
 * MekBuilder - unit design companion of MegaMek
 * Copyright (C) 2017 The MegaMek Team
 * <p>
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 * <p>
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * <p>
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 */
package org.megamek.mekbuilder.unit;

/**
 * Values for classifying units by weight. The range for each weight class varies by unit type, and not
 * all weight classes are used for every unit type.
 */
public enum UnitWeightClass {
    ULTRALIGHT(0, "u"),
    LIGHT(1, "l"),
    MEDIUM(2, "m"),
    HEAVY(3, "h"),
    ASSAULT(4, "a"),
    SUPERHEAVY(5, "s");

    public final int value;
    public final String key;

    UnitWeightClass(int value, String key) {
        this.value = value;
        this.key = key;
    }
}
