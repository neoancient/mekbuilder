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

/**
 * Manages all heat sinks on the unit. Interacts with the engine to determine total heat capacity
 * and slot requirements for Meks.
 */

public class HeatSinkMount extends CompoundMount {

    public HeatSinkMount(UnitBuild unit, HeatSink heatSinkType) {
        super(unit, heatSinkType);
    }

    public HeatSink getHeatSinkType() {
        return (HeatSink) getComponent();
    }

    public int totalHeatSinks() {
        return getCount() + getUnit().getEngine().weightFreeHeatSinks();
    }

    @Override
    public int getComponentSlots() {
        if (getUnit().getUnitType().isMech()) {
            return getHeatSinkType().calcSlots(getUnit(), Math.max(0, totalHeatSinks() - integratedSlots()));
        }
        return 0;
    }

    /**
     * @return The amount of heat that can be dissipated by all mounted heat sinks.
     */
    @Override
    public int heatDissipation() {
        return totalHeatSinks() * getHeatSinkType().heatDissipation();
    }

    /**
     * The number of heat sinks that are considered integral to the engine and do not have to be
     * assigned critical slots is 4% of the engine rating, or twice that for compact slots.
     * This is only applicable to meks.
     *
     * @return The number of heat sinks that do not have to be assigned mek critical slots.
     */
    public int integratedSlots() {
        int integrated = getUnit().getEngine().getEngineRating() / 25;
        if (getHeatSinkType().isCompact()) {
            integrated *= 2;
        }
        return integrated;
    }
}
