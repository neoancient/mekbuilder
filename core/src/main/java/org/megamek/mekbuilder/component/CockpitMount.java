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

import com.sun.istack.NotNull;
import org.megamek.mekbuilder.unit.UnitBuild;
import org.megamek.mekbuilder.unit.UnitLocation;

/**
 * Distinguishes among cockpit, sensors, and life support criticals.
 */
public class CockpitMount extends DistributedMount {

    public CockpitMount(UnitBuild build, String cockpitKey) {
        this(build, (Cockpit) ComponentLibrary.getInstance().getComponent(cockpitKey));
    }

    public CockpitMount(UnitBuild build, Cockpit cockpit) {
        super(build, cockpit);
    }

    public Cockpit getCockpit() {
        return (Cockpit) getComponent();
    }

    public void setCockpitType(@NotNull Cockpit cockpitType) {
        if ((null != cockpitType) && !getCockpit().equals(cockpitType)) {
            setComponent(cockpitType);
            updateLocations();
        }
    }

    public int getCockpitSlots(UnitLocation loc) {
        return getCockpit().fixedCockpitSlots(getUnit(), loc);
    }

    public int getSensorSlots(UnitLocation loc) {
        return getCockpit().fixedSensorSlots(getUnit(), loc);
    }

    public int getLifeSupportSlots(UnitLocation loc) {
        return getCockpit().fixedLifeSupportSlots(getUnit(), loc);
    }

    public double getSensorCost() {
        return getUnit().getTonnage() * 2000;
    }

    public double getLifeSupportCost() {
        return 50000;
    }

    @Override
    public boolean isInLocation(UnitLocation loc) {
        return getCockpitSlots(loc) + getSensorSlots(loc) + getLifeSupportSlots(loc) > 0;
    }
}
