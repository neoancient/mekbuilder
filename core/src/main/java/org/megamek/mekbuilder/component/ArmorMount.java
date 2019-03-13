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
package org.megamek.mekbuilder.component;

import org.megamek.mekbuilder.unit.UnitBuild;
import org.megamek.mekbuilder.unit.UnitLocation;

import java.util.EnumMap;
import java.util.Map;

/**
 * Mount class to track total armor tonnage and distribution of armor points and critical slots
 * to various locations.
 */
public class ArmorMount extends DistributedMount {

    private final Map<UnitLocation,Integer> armorPoints;
    private final Map<UnitLocation,Integer> rearArmorPoints;
    private double armorTonnage = 0;
    private boolean patchwork = false;
    private final Map<UnitLocation, Armor> armorByLocation;

    public ArmorMount(UnitBuild unit, Armor armor) {
        super(unit, armor);
        armorPoints = new EnumMap<>(UnitLocation.class);
        rearArmorPoints = new EnumMap<>(UnitLocation.class);
        armorByLocation = new EnumMap<>(UnitLocation.class);
        for (UnitLocation loc : unit.getLocationSet()) {
            if (loc.isArmored()) {
                armorPoints.put(loc, 0);
                armorByLocation.put(loc, armor);
            }
            if (loc.hasRearArmor()) {
                rearArmorPoints.put(loc, 0);
            }
        }
        setPatchwork(armor.hasFlag(ComponentSwitch.PATCHWORK_ARMOR));
    }

    public boolean isPatchwork() {
        return patchwork;
    }

    public void setPatchwork(boolean patchwork) {
        if (patchwork == this.patchwork) {
            return;
        }
        if (!patchwork) {
            Armor standard = (Armor) ComponentLibrary.getInstance().getComponent(getUnit().getDefaultArmorName());
            for (UnitLocation loc : armorByLocation.keySet()) {
                armorByLocation.put(loc, standard);
            }
        }
        this.patchwork = patchwork;
    }

    @Override
    public double getComponentWeight() {
        return armorTonnage;
    }

    public int getTotalArmorPoints() {
        return (int)(armorTonnage * getPointsPerTon());
    }

    public double getWeightPerPoint() {
        return getComponent().calcWeight(getUnit());
    }

    public double getPointsPerTon() {
        double wpp = getComponent().calcWeight(getUnit());
        if (wpp > 0) {
            return 1.0 / wpp;
        } else {
            return 0;
        }
    }

    public int getAllocationArmorPoints() {
        return armorPoints.values().stream().mapToInt(Integer::intValue).sum()
                + rearArmorPoints.values().stream().mapToInt(Integer::intValue).sum();
    }

    public int getArmorPoints(UnitLocation loc) {
        return armorPoints.get(loc);
    }

    public int getRearArmorPoints(UnitLocation loc) {
        return rearArmorPoints.get(loc);
    }

    @Override
    public int fixedSlots(UnitLocation loc) {
        if (patchwork) {
            if (armorByLocation.containsKey(loc)) {
                return armorByLocation.get(loc).patchworkSlots(getUnit());
            } else {
                return 0;
            }
        } else {
            return super.fixedSlots(loc);
        }
    }
}
