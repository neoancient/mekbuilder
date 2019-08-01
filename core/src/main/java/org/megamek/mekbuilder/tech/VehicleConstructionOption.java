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
import org.megamek.mekbuilder.unit.MotiveType;
import org.megamek.mekbuilder.unit.UnitType;

/**
 *
 */
@JsonIgnoreProperties(ignoreUnknown=true)
public class VehicleConstructionOption extends UnitConstructionOption {

    private final MotiveType motiveType;

    @JsonCreator
    @SuppressWarnings("unused")
    VehicleConstructionOption() {
        this(new TechProgression(),
                UnitType.BATTLE_MEK, 0.0, 0.0, 0.0, null, null,
                MotiveType.WHEELED);
    }

    public VehicleConstructionOption(TechProgression techProgression,
                              UnitType unitType, double minWeight, double maxWeight, double weightIncrement,
                              ConstructionOptionKey prevWeightKey, ConstructionOptionKey nextWeightKey,
                              MotiveType motiveType) {
        super(techProgression, unitType, minWeight, maxWeight, weightIncrement, prevWeightKey, nextWeightKey);
        this.motiveType = motiveType;
    }

    public MotiveType getMotiveType() {
        return motiveType;
    }
}
