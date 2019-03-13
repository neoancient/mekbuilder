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

/**
 * Mount for engine that also handles engine rating and number of integrated heat sinks.
 */
public class MekEngineMount extends Mount implements IEngineMount {

    private int integratedHeatSinks;

    public MekEngineMount(UnitBuild unit, MVFEngine component) {
        this(unit, component, 10);
    }

    public MekEngineMount(UnitBuild build, Component component, int rating) {
        super(build, component);
    }

    public MVFEngine getEngine() {
        return (MVFEngine) getComponent();
    }

    @Override
    public void setComponent(Component component) {
        if (!(component instanceof MVFEngine)) {
            throw new IllegalArgumentException("Attempting to assign non-engine component to engine mount.");
        }
        super.setComponent(component);
    }

    @Override
    public int getEngineRating() {
        return (int)getSize();
    }

    @Override
    public double getEngineTonnage() {
        return getComponentWeight();
    }

    @Override
    public boolean isEngineLocation(UnitLocation loc) {
        return isInLocation(loc);
    }

    @Override
    public boolean isInLocation(UnitLocation loc) {
        return getComponent().fixedSlots(getUnit(), loc) > 0;
    }

    public void setSize(int rating) {
        rating -= rating % 5;
        if (rating < 10) {
            rating = 10;
        }
        if (rating <= 400) {
            if (getEngine().hasFlag(ComponentSwitch.LARGE_ENGINE)) {
                setComponent(getEngine().standardSize());
            }
            super.setSize(rating);
        } else {
            rating = Math.min(500, rating);
            if (!getEngine().hasFlag(ComponentSwitch.LARGE_ENGINE)) {
                if (getEngine().hasFlag(ComponentSwitch.HAS_LARGE_ENGINE)) {
                    setComponent(getEngine().largeSize());
                } else {
                    rating = 400;
                }
            }
            super.setSize(rating);
        }
    }

    public void setRating(int rating) {
        setSize(rating);
    }

    /**
     * @return The number of heat sinks that do not have to be assigned critical slots; in OmniMeks
     *         this is limited to the number in the base chassis, which may be lower than the maximum.
     */
    public int integratedHSCount() {
        return integratedHeatSinks;
    }

    /**
     * Set the number of heat sinks that do not have to be assigned to critical slots.
     *
     * @param count The number of heat sinks that do not have be assigned critical slots.
     */
    public void setIntegratedHSCount(int count) {
        integratedHeatSinks = count;
    }

    /**
     * @return The number of heat sinks that can be integrated into the engine.
     */
    public int maxIntegratedHS() {
        return (int)Math.floor(getEngineRating() / 25);
    }
}