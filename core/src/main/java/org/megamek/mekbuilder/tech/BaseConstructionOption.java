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
import org.megamek.mekbuilder.unit.UnitType;

/**
 * Each unit has a single base construction option that determines its base tech progression and weight
 * limits. Where weight classes have different tech progression values, these are separate options
 * which track the previous and next options for lighter and heavier units, respectively.
 */
@JsonIgnoreProperties(ignoreUnknown=true)
public class BaseConstructionOption implements ITechDelegator {

    private final Keys key;
    private final UnitType unitType;
    private final double minWeight;
    private final double maxWeight;
    private final Keys prevWeightKey;
    private final Keys nextWeightKey;
    private final TechProgression techProgression;

    @JsonCreator
    BaseConstructionOption() {
        this(null, null, 0.0, 0.0, null, null, null);
    }

    BaseConstructionOption(Keys key, UnitType unitType, double minWeight, double maxWeight,
                                  Keys prevWeightKey, Keys nextWeightKey, TechProgression techProgression) {
        this.key = key;
        this.unitType = unitType;
        this.minWeight = minWeight;
        this.maxWeight = maxWeight;
        this.prevWeightKey = prevWeightKey;
        this.nextWeightKey = nextWeightKey;
        this.techProgression = techProgression;
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

    @Override
    public ITechProgression techDelegate() {
        return techProgression;
    }

    public enum Keys {
        MEK_PRIMITIVE,
        MEK_STANDARD,
        MEK_BIMODAL_LAM,
        MEK_STANDARD_LAM,
        MEK_QUADVEE,
        MEK_TRIPOD,
        MEK_SUPERHEAVY,
        MEK_SUPERHEAVY_TRIPOD,
        MEK_ULTRALIGHT,

        IMEK_PRIMITIVE,
        IMEK_STANDARD,
        IMEK_SUPERHEAVY,
        IMEK_TRIPOD,
        IMEK_SUPERHEAVY_TRIPOD,

        PROTOMEK_STANDARD,
        PROTOMEK_QUAD,
        PROTOMEK_GLIDER,
        PROTOMEK_ULTRAHEAVY,
        PROTOMEK_QUAD_ULTRAHEAVY,
        PROTOMEK_GLIDER_ULTRAHEAVY,

        BA_EXOSKELETON,
        BA_PAL,
        BA_LIGHT,
        BA_MEDIUM,
        BA_HEAVY,
        BA_ASSAULT,

        VEE_PRIMITIVE,
        CV_WHEELED,
        CV_TRACKED,
        CV_HOVER,
        CV_VTOL,
        CV_NAVAL_DISPLACEMENT,
        CV_NAVAL_HYDROFOIL,
        CV_NAVAL_SUBMARINE,
        CV_WIGE,
        CV_SUPERHEAVY_WHEELED,
        CV_SUPERHEAVY_TRACKED,
        CV_SUPERHEAVY_HOVER,
        CV_SUPERHEAVY_VTOL,
        CV_SUPERHEAVY_NAVAL_DISPLACEMENT,
        CV_SUPERHEAVY_NAVAL_HYDROFOIL,
        CV_SUPERHEAVY_NAVAL_SUBMARINE,
        CV_SUPERHEAVY_WIGE,

        SV_WHEELED,
        SV_TRACKED,
        SV_HOVER,
        SV_VTOL,
        SV_NAVAL,
        SV_WIGE,
        SV_FIXED_WING,
        SV_AIRSHIP,
        SV_RAIL,
        SV_SATELLITE,

        ASF_PRIMITIVE,
        ASF_STANDARD,
        CF_PRIMITIVE,
        CF_STANDARD,
        SC_PRIMITIVE,
        SC_STANDARD,
        DS_PRIMITIVE,
        DS_STANDARD,
        JS_PRIMTIVE,
        JS_STANDARD,
        WARSHIP,
        SPACE_STATION_STANDARD,
        SPACE_STATION_MODULAR,

        MS_STANDARD
    }
}
