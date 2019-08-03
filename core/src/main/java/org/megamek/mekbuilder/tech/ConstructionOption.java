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

import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * Class for tracking tech progression of various generic construction options. Base class for unit-specific
 * construction options.
 */
@JsonIgnoreProperties(ignoreUnknown=true)
public class ConstructionOption implements ITechDelegator {
    private final ConstructionOptionKey key;
    @JsonSerialize(using = TechProgression.Serializer.class)
    @JsonDeserialize(using = TechProgression.Deserializer.class)
    private final TechProgression techProgression;

    @JsonCreator
    @SuppressWarnings("unused")
    ConstructionOption() {
        this(null, new TechProgression());
    }

    public ConstructionOption(ConstructionOptionKey key, TechProgression techProgression) {
        this.key = key;
        this.techProgression = techProgression;
    }

    public ConstructionOptionKey getKey() {
        return key;
    }

    @Override
    public ITechProgression techDelegate() {
        return techProgression;
    }

}
