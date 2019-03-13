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

import java.util.Collections;
import java.util.EnumMap;
import java.util.Map;

/**
 * Used by components that can have their slots assigned to multiple locations, such as structure or armor.
 */
public class DistributedMount extends Mount {

    private final Map<UnitLocation, Integer> locations;

    public DistributedMount(UnitBuild build, Component component) {
        super(build, component);
        locations = new EnumMap<>(UnitLocation.class);
    }

    @Override
    public void changeLocation(UnitLocation oldLoc, UnitLocation newLoc) {
        if (locations.containsKey(oldLoc)) {
            locations.merge(newLoc, locations.get(oldLoc), Integer::sum);
            locations.remove(oldLoc);
        }
    }

    @Override
    public void changeLocation(UnitLocation oldLoc, UnitLocation newLoc, int slots) {
        if (locations.containsKey(oldLoc)) {
            int toMove = Math.min(locations.get(oldLoc), slots);
            locations.merge(newLoc, toMove, Integer::sum);
            if (locations.get(oldLoc) > toMove) {
                locations.merge(oldLoc, -toMove, Integer::sum);
            } else {
                locations.remove(oldLoc);
            }
        }
    }

    public Map<UnitLocation,Integer> getLocations() {
        return Collections.unmodifiableMap(locations);
    }

    public void updateLocations() {
        if (getComponent().locationFixed()) {
            locations.clear();
        }
        for (UnitLocation loc : getUnit().getLocationSet()) {
            int slots = fixedSlots(loc);
            if (slots > 0) {
                locations.put(loc, slots);
            }
        }
    }

    /**
     * The number of fixed slots in a location is set by the component, but patchwork armor
     * needs to override this to select the correct component for the location.
     *
     * @param loc
     * @return The number of slots that the component requires in the specific location.
     */
    public int fixedSlots(UnitLocation loc) {
        return getComponent().fixedSlots(getUnit(), loc);
    }

    @Override
    public boolean isInLocation(UnitLocation loc) {
        return locations.containsKey(loc);
    }
}
