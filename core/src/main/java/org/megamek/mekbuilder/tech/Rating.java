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
package org.megamek.mekbuilder.tech;

/**
 * Rating system used for tech rating and availability
 */
public enum Rating {
    RATING_A ("A"),
    RATING_B ("B"),
    RATING_C ("C"),
    RATING_D ("D"),
    RATING_E ("E"),
    RATING_F ("F"),
    RATING_FSTAR ("F*"),
    RATING_X ("X");

    public final String displayName;

    Rating(String displayName) {
        this.displayName = displayName;
    }

    public Rating plus(int addend) {
        return values()[Math.max(0, Math.min(ordinal() + addend, values().length - 1))];
    }
}
