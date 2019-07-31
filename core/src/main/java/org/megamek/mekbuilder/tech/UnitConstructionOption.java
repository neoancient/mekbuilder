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
package org.megamek.mekbuilder.tech;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.sun.istack.Nullable;
import org.megamek.mekbuilder.unit.UnitType;

/**
 * Each unit has a single base construction option that determines its base tech progression and weight
 * limits. Where weight classes have different tech progression values, these are separate options
 * which track the previous and next options for lighter and heavier units, respectively.
 */
@JsonIgnoreProperties(ignoreUnknown=true)
public class UnitConstructionOption extends ConstructionOption {

    private final UnitType unitType;
    private final double minWeight;
    private final double maxWeight;
    private final @Nullable ConstructionOptionKey prevWeightKey;
    private final @Nullable ConstructionOptionKey nextWeightKey;

    @JsonCreator
    UnitConstructionOption() {
        this(ConstructionOptionKey.MEK_STANDARD, new TechProgression(),
                UnitType.BATTLE_MEK, 0.0, 0.0, null, null);
    }

    UnitConstructionOption(ConstructionOptionKey key, TechProgression techProgression,
                           UnitType unitType, double minWeight, double maxWeight,
                           ConstructionOptionKey prevWeightKey, ConstructionOptionKey nextWeightKey) {
        super(key, techProgression);
        this.unitType = unitType;
        this.minWeight = minWeight;
        this.maxWeight = maxWeight;
        this.prevWeightKey = prevWeightKey;
        this.nextWeightKey = nextWeightKey;
    }

    public UnitType getUnitType() {
        return unitType;
    }

    public double getMinWeight() {
        return minWeight;
    }

    public double getMaxWeight() {
        return maxWeight;
    }

}
