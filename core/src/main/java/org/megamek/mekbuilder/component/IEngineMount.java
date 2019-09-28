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

import org.megamek.mekbuilder.unit.UnitLocation;

/**
 *
 */
public interface IEngineMount {
    /**
     * The engine rating is only valid for MVFEngines.
     *
     * @return The rating of the mounted engine.
     */
    int getEngineRating();

    /**
     * @return The weight of the engine in tons
     */
    double getEngineTonnage();

    /**
     * Check for equipment that must be mounted in the same location as the engine, or is forbidden
     * from such a location. This can vary with unit and engine type.
     *
     * @param loc The location to check
     * @return    Whether the location contains at least part of the engine.
     */
    boolean isEngineLocation(UnitLocation loc);

    /**
     * @return The number of heat sinks that are provided automatically with the engine.
     */
    int weightFreeHeatSinks();
}
