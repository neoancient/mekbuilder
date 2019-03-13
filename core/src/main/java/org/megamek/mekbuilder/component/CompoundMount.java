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
 * Combined mounting for several components of the same time that it's more convenient to hold
 * together, such as jump jets and heat sinks.
 */
public class CompoundMount extends DistributedMount {
    private int count = 0;
    private int fixedCount = 0;

    public CompoundMount(UnitBuild entity, Component component) {
        super(entity, component);
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getFixedCount() {
        return fixedCount;
    }

    public void setFixedCount(int count) {
        this.fixedCount = count;
    }

    public double getSlotsPerItem() {
        return getComponent().calcSlots(getUnit());
    }

    @Override
    public double getComponentWeight() {
        return getComponent().calcWeight(getUnit(), count);
    }

    @Override
    public double getComponentCost() {
        return getComponent().calcCost(getUnit(), count);
    }

}
