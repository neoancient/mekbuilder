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
    BATTLE_MEK("BM", HeatStrategy.ACCUMULATE),
    INDUSTRIAL_MEK("IM", HeatStrategy.ACCUMULATE),
    PROTOMEK("PM", HeatStrategy.HEAT_NEUTRAL),
    COMBAT_VEHICLE("CV", HeatStrategy.HEAT_NEUTRAL),
    SUPPORT_VEHICLE("SV", HeatStrategy.HEAT_NEUTRAL),
    BATTLE_ARMOR("BA", HeatStrategy.NOT_TRACKED),
    CONV_INFANTRY("CI", HeatStrategy.NOT_TRACKED),
    ASF("AF", HeatStrategy.ACCUMULATE),
    CONV_FIGHTER("CF", HeatStrategy.HEAT_NEUTRAL),
    SMALL_CRAFT("SC", HeatStrategy.ACCUMULATE),
    DROPSHIP("DS", HeatStrategy.ZERO_HEAT),
    JUMPSHIP("JS", HeatStrategy.ZERO_HEAT),
    WARSHIP("WS", HeatStrategy.ZERO_HEAT),
    SPACE_STATION("SS", HeatStrategy.ZERO_HEAT),
    MOBILE_STRUCTURE("MS", HeatStrategy.HEAT_NEUTRAL),
    HANDHELD_WEAPON("HHW", HeatStrategy.HEAT_NEUTRAL);

    public final String abbrev;
    public final HeatStrategy heatStrategy;

    UnitType(String abbrev, HeatStrategy heatStrategy) {
        this.abbrev = abbrev;
        this.heatStrategy = heatStrategy;
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
