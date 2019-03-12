/*
 * MekBuilder - unit design companion of MegaMek
 * Copyright (C) 2017 The MegaMek Team
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
package org.megamek.mekbuilder.unit;

import java.util.Set;

/**
 * Base class for unit types
 */

public abstract class UnitBuild {

    public boolean isBiped() {
        return false;
    }
    public boolean isTripod() {
        return false;
    }
    public boolean isQuad() {
        return false;
    }
    abstract public UnitType getUnitType();

    abstract public boolean usesKilogramStandard();

    abstract public double getTonnage();
    abstract public double getStructureTonnage();
    abstract public double getArmorTonnage();
    abstract public double getTotalArmorPoints();
    abstract public String getDefaultArmorName();
    abstract public int getMaxArmorPoints(UnitLocation loc);
    abstract public UnitWeightClass getWeightClass();
    abstract public IEngineMount getEngine();
    abstract public int getWalkMP();
    abstract public Set<UnitLocation> getLocationSet();
    abstract public boolean isCockpitLocation(UnitLocation loc);
    abstract public double getWeaponTonnage();
    abstract public double getTCLinkedTonnage();
    abstract public double getWeaponTonnage(UnitLocation loc);
    abstract public double getEnergyWeaponTonnage();
}
