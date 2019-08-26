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
 * Unit types. These categories are the ones used by construction rules to identify which unit types can
 * legally use a component.
 */
public enum UnitType {
    BATTLE_MEK("BM"),
    INDUSTRIAL_MEK("IM"),
    PROTOMEK("PM"),
    COMBAT_VEHICLE("CV"),
    SUPPORT_VEHICLE("SV"),
    BATTLE_ARMOR("BA"),
    CONV_INFANTRY("CI"),
    ASF("AF"),
    CONV_FIGHTER("CF"),
    SMALL_CRAFT("SC"),
    DROPSHIP("DS"),
    JUMPSHIP("JS"),
    WARSHIP("WS"),
    SPACE_STATION("SS"),
    MOBILE_STRUCTURE("MS"),
    HANDHELD_WEAPON("HHW");

    public final String abbrev;

    UnitType(String abbrev) {
        this.abbrev = abbrev;
    }

    public boolean isMech() {
        return this == BATTLE_MEK || this == INDUSTRIAL_MEK;
    }

    public boolean isVehicle() {
        return this == COMBAT_VEHICLE || this == SUPPORT_VEHICLE;
    }

    public boolean isInfantry() {
        return this == CONV_INFANTRY || this == BATTLE_ARMOR;
    }

    public boolean isFighter() {
        return this == ASF || this == CONV_FIGHTER;
    }

    public boolean isLargeCraft() {
        return this == DROPSHIP || isCapitalShip();
    }

    public boolean isCapitalShip() {
        return this == JUMPSHIP || this == WARSHIP || this == SPACE_STATION;
    }
}
