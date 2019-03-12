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
package org.megamek.mekbuilder.component;

import com.fasterxml.jackson.annotation.JsonCreator;

import java.util.List;

/**
 * Used to track size of range bands in hexes.
 */
public class WeaponRange {
    private final int minRange;
    private final int shortRange;
    private final int medRange;
    private final int longRange;
    private final int extremeRange;

    @JsonCreator
    private WeaponRange() {
        this(0, 0, 0, 0, 0);
    }

    public WeaponRange(int minRange, int shortRange, int medRange, int longRange, int extremeRange) {
        this.minRange = minRange;
        this.shortRange = shortRange;
        this.medRange = medRange;
        this.longRange = longRange;
        this.extremeRange = extremeRange;
    }

    /**
     * Contructs a WeaponRange from a {@link List}. If the List has fewer than five values, zero is used
     * for those range bands.
     *
     * @param ranges A list of range bands
     */
    public WeaponRange(List<Integer> ranges) {
        this(
                ranges.size() > 0 ? ranges.get(0) : 0,
                ranges.size() > 1 ? ranges.get(1) : 0,
                ranges.size() > 2 ? ranges.get(2) : 0,
                ranges.size() > 3 ? ranges.get(3) : 0,
                ranges.size() > 4 ? ranges.get(4) : 0
        );
    }
}
