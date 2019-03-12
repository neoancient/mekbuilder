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

import org.megamek.mekbuilder.unit.UnitBuild;
import org.megamek.mekbuilder.unit.UnitLocation;

import java.util.EnumMap;
import java.util.Map;

/**
 * Control systems for units. Tracks size and location of life support and sensor criticals for Mechs.
 */
public class Cockpit extends Component {
    private Map<UnitLocation, Integer> sensorLocations = new EnumMap<>(UnitLocation.class);
    private Map<UnitLocation, Integer> lifeSupportLocations = new EnumMap<>(UnitLocation.class);
    private boolean torsoMounted = false;

    /**
     * Only used for Mech cockpits
     *
     * @return Whether the cockpit is mounted in the torso
     */
    public boolean isTorsoMounted() {
        return torsoMounted;
    }

    @Override
    public int fixedSlots(UnitBuild unit, UnitLocation location) {
        return fixedCockpitSlots(unit, location)
                + fixedSensorSlots(unit, location)
                + fixedLifeSupportSlots(unit, location);
    }

    /**
     * @param unit The unit mounting the cockpit
     * @param loc  The location to check
     * @return     The number of slots in the location occupied by the cockpit itself
     */
    public int fixedCockpitSlots(UnitBuild unit, UnitLocation loc) {
        return super.fixedSlots(unit, loc);
    }

    /**
     * @param unit The unit mounting the cockpit
     * @param loc  The location to check
     * @return     The number of slots in the location occupied by sensors
     */
    public int fixedSensorSlots(UnitBuild unit, UnitLocation loc) {
        return sensorLocations.getOrDefault(loc, 0);
    }

    /**
     * @param unit The unit mounting the cockpit
     * @param loc  The location to check
     * @return     The number of slots in the location occupied by life support
     */
    public int fixedLifeSupportSlots(UnitBuild unit, UnitLocation loc) {
        return lifeSupportLocations.getOrDefault(loc, 0);
    }
}
