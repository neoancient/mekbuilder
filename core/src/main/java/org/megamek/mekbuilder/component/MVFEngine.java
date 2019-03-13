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

import megamek.common.annotations.Nullable;
import org.megamek.mekbuilder.unit.UnitBuild;
import org.megamek.mekbuilder.unit.UnitType;
import org.megamek.mekbuilder.utilities.Round;

/**
 * Engines for Mechs (and ProtoMechs), vehicles, and fighters.
 */
public class MVFEngine extends Component {

    /**
     * The weight of a standard fusion engine is ENGINE_WEIGHTS[rating / 5] tons
     */
    private static final double[] ENGINE_WEIGHTS = {
        0.0, 0.5, 0.5, 0.5, 0.5, 0.5, 1.0, 1.0, 1.0, 1.0, 1.5, 1.5, 1.5, 2.0, 2.0, 2.0, 2.5, 2.5, 3.0, 3.0, 3.0,
        3.5, 3.5, 4.0, 4.0, 4.0, 4.5, 4.5, 5.0, 5.0, 5.5, 5.5, 6.0, 6.0, 6.0, 70.0, 7.0, 7.5, 7.5, 8.0, 8.5, 8.5,
        9.0, 9.5, 10.0, 10.0, 10.5, 11.0, 11.5, 12.0, 12.5, 13.0, 13.5, 14.0, 14.5, 15.5, 16.0, 16.5, 17.5, 18.0, 19.0,
        19.5, 20.5, 21.5, 22.5, 23.5, 24.5, 25.5, 27.0, 28.5, 29.5, 31.5, 33.0, 34.5, 36.5, 38.5, 41.0, 43.5, 46.0, 49.0, 52.5,
        56.5, 61.0, 66.5, 72.5, 79.5, 87.5, 97.0, 107.5, 119.5, 133.5, 150.0, 168.5, 190.0, 214.5, 243.0, 275.5, 313.0, 356.0, 405.5, 462.5 };

    private int weightFreeHeatSinks = 0;

    /**
     * @return The number of heat sinks that are included in the weight of the engine
     */
    public int getWeightFreeHeatSinks() {
        return weightFreeHeatSinks;
    }

    public double getWeight(UnitBuild unit) {
        if ((unit.getUnitType().equals(UnitType.PROTOMEK)
                && unit.getEngine().getEngineRating() < 40)) {
            return unit.getEngine().getEngineRating() * 0.025;
        } else {
            return Round.round(ENGINE_WEIGHTS[(int) Math.ceil(unit.getEngine().getEngineRating() / 5)]
                    * getWeightFactor(), unit);
        }
    }

    @Override
    public double calcCost(UnitBuild unit, double size) {
        return getCostFactor() * size * unit.getTonnage() / 75.0;
    }

    public boolean isLargeEngine() {
        return hasFlag(ComponentSwitch.LARGE_ENGINE);
    }

    public boolean isFusionEngine() {
        return hasFlag(ComponentSwitch.FUSION);
    }

    /**
     * Engines with a large engine variant have a maximum rating of 500, all others have a maximum of 400.
     *
     * @return The maximum rating of this engine type.
     */
    public int maxRating() {
        if (hasFlag(ComponentSwitch.LARGE_ENGINE) || hasFlag(ComponentSwitch.HAS_LARGE_ENGINE)) {
            return 500;
        } else {
            return 400;
        }
    }

    /**
     * @return The standard size equivalent of this engine
     */
    public MVFEngine standardSize() {
        if (isLargeEngine()) {
            return (MVFEngine) ComponentLibrary.getInstance()
                    .getComponent(getInternalName().replaceFirst("Large", ""));
        } else {
            return this;
        }
    }

    /**
     * @return The large equipvalent of this engine, {@code null} if there is no large version.
     */
    public @Nullable MVFEngine largeSize() {
        if (isLargeEngine()) {
            return this;
        } else {
            return (MVFEngine) ComponentLibrary.getInstance()
                    .getComponent("Large" + getInternalName());
        }
    }
}
