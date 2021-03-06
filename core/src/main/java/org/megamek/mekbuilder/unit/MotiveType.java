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
 * Movemement modes available to units.
 */
public enum MotiveType {
    GROUND("-"),
    JUMP("j"),
    QUADVEE_TRACKED("qt"),
    QUADVEE_WHEELED("qw"),
    NAVAL("n"),
    SUBMERSIBLE("s"),
    WHEELED("w"),
    TRACKED("t"),
    HOVER("h"),
    VTOL("v"),
    WIGE("g"),
    RAIL("r"),
    WHEELED_BICYCLE("w(b)"),
    WHEELED_MONOCYCLE("w(m)"),
    MOTORIZED("m"),
    AIRSHIP("i"),
    AERODYNE("a"),
    SPHEROID("p"),
    AIRMEK("l"),
    STATION_KEEPING("k"),
    IMMOBILE("x");

    private final String asCode;

    MotiveType(String asCode) {
        this.asCode = asCode;
    }

    /**
     * @return The code used by Alpha Strike for the movement type.
     */
    public String alphaStrikeCode() {
        return asCode;
    }
}
